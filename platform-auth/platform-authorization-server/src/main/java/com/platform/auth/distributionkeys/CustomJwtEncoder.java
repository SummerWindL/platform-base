package com.platform.auth.distributionkeys;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.factories.DefaultJWSSignerFactory;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.produce.JWSSignerFactory;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 代码几乎都是 NimbusJwsEncoder 这个类的
 *
 * @author yanl.fu 20210905
 */
public class CustomJwtEncoder implements JwtEncoder {

    private static final String ENCODING_ERROR_MESSAGE_TEMPLATE = "An error occurred while attempting to encode the Jwt: %s";

    private static final Converter<JoseHeader, JWSHeader> JWS_HEADER_CONVERTER = new JwsHeaderConverter();

    private static final Converter<JwtClaimsSet, JWTClaimsSet> JWT_CLAIMS_SET_CONVERTER = new JwtClaimsSetConverter();

    private static final JWSSignerFactory JWS_SIGNER_FACTORY = new DefaultJWSSignerFactory();

    private final Map<JWK, JWSSigner> jwsSigners = new ConcurrentHashMap<>();

    private final JWKSource<SecurityContext> jwkSource;

    /**
     * Constructs a {@code NimbusJwsEncoder} using the provided parameters.
     *
     * @param jwkSource the {@code com.nimbusds.jose.jwk.source.JWKSource}
     */
    public CustomJwtEncoder(JWKSource<SecurityContext> jwkSource) {
        Assert.notNull(jwkSource, "jwkSource cannot be null");
        this.jwkSource = jwkSource;
    }

    @Override
    public Jwt encode(JoseHeader headers, JwtClaimsSet claims) throws JwtEncodingException {
        Assert.notNull(headers, "headers cannot be null");
        Assert.notNull(claims, "claims cannot be null");

        JWK jwk = selectJwk(headers);
        if (jwk == null) {
            throw new JwtEncodingException(
                    String.format(ENCODING_ERROR_MESSAGE_TEMPLATE, "Failed to select a JWK signing key"));
        } else if (!StringUtils.hasText(jwk.getKeyID())) {
            throw new JwtEncodingException(String.format(ENCODING_ERROR_MESSAGE_TEMPLATE,
                    "The \"kid\" (key ID) from the selected JWK cannot be empty"));
        }

        // @formatter:off
        headers = JoseHeader.from(headers)
                .type(JOSEObjectType.JWT.getType())
                .keyId(jwk.getKeyID())
                .build();
        claims = JwtClaimsSet.from(claims)
                .id(UUID.randomUUID().toString())
                .build();
        // @formatter:on

        JWSHeader jwsHeader = JWS_HEADER_CONVERTER.convert(headers);
        JWTClaimsSet jwtClaimsSet = JWT_CLAIMS_SET_CONVERTER.convert(claims);

        JWSSigner jwsSigner = this.jwsSigners.computeIfAbsent(jwk, (key) -> {
            try {
                return JWS_SIGNER_FACTORY.createJWSSigner(key);
            } catch (JOSEException ex) {
                throw new JwtEncodingException(String.format(ENCODING_ERROR_MESSAGE_TEMPLATE,
                        "Failed to create a JWS Signer -> " + ex.getMessage()), ex);
            }
        });

        SignedJWT signedJwt = new SignedJWT(jwsHeader, jwtClaimsSet);
        try {
            signedJwt.sign(jwsSigner);
        } catch (JOSEException ex) {
            throw new JwtEncodingException(
                    String.format(ENCODING_ERROR_MESSAGE_TEMPLATE, "Failed to sign the JWT -> " + ex.getMessage()), ex);
        }
        String jws = signedJwt.serialize();

        return new Jwt(jws, claims.getIssuedAt(), claims.getExpiresAt(), headers.getHeaders(), claims.getClaims());
    }

    private JWK selectJwk(JoseHeader headers) {
        // ================================================ 重写的
        JWSAlgorithm jwsAlgorithm = JWSAlgorithm.parse(headers.getAlgorithm().getName());
        Map<String, Object> params = new HashMap<>(3);
        params.put("kid", headers.getHeaders().get("client-id"));
        params.put("alg", jwsAlgorithm.getName());
        // ================================================
        JWSHeader jwsHeader;
        try {
            jwsHeader = JWSHeader.parse(params);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JWKSelector jwkSelector = new JWKSelector(JWKMatcher.forJWSHeader(jwsHeader));

        List<JWK> jwks;
        try {
            jwks = this.jwkSource.get(jwkSelector, null);
        } catch (KeySourceException ex) {
            throw new JwtEncodingException(String.format(ENCODING_ERROR_MESSAGE_TEMPLATE,
                    "Failed to select a JWK signing key -> " + ex.getMessage()), ex);
        }

        if (jwks.size() > 1) {
            throw new JwtEncodingException(String.format(ENCODING_ERROR_MESSAGE_TEMPLATE,
                    "Found multiple JWK signing keys for algorithm '" + jwsAlgorithm.getName() + "'"));
        }

        return !jwks.isEmpty() ? jwks.get(0) : null;
    }

    private static class JwsHeaderConverter implements Converter<JoseHeader, JWSHeader> {

        @Override
        public JWSHeader convert(JoseHeader headers) {
            JWSHeader.Builder builder = new JWSHeader.Builder(JWSAlgorithm.parse(headers.getAlgorithm().getName()));

            Set<String> critical = headers.getCritical();
            if (!CollectionUtils.isEmpty(critical)) {
                builder.criticalParams(critical);
            }

            String contentType = headers.getContentType();
            if (StringUtils.hasText(contentType)) {
                builder.contentType(contentType);
            }

            URL jwkSetUri = headers.getJwkSetUrl();
            if (jwkSetUri != null) {
                try {
                    builder.jwkURL(jwkSetUri.toURI());
                } catch (Exception ex) {
                    throw new JwtEncodingException(String.format(ENCODING_ERROR_MESSAGE_TEMPLATE,
                            "Failed to convert '" + JoseHeaderNames.JKU + "' JOSE header to a URI"), ex);
                }
            }

            Map<String, Object> jwk = headers.getJwk();
            if (!CollectionUtils.isEmpty(jwk)) {
                try {
                    builder.jwk(JWK.parse(jwk));
                } catch (Exception ex) {
                    throw new JwtEncodingException(String.format(ENCODING_ERROR_MESSAGE_TEMPLATE,
                            "Failed to convert '" + JoseHeaderNames.JWK + "' JOSE header"), ex);
                }
            }

            String keyId = headers.getKeyId();
            if (StringUtils.hasText(keyId)) {
                builder.keyID(keyId);
            }

            String type = headers.getType();
            if (StringUtils.hasText(type)) {
                builder.type(new JOSEObjectType(type));
            }

            List<String> x509CertificateChain = headers.getX509CertificateChain();
            if (!CollectionUtils.isEmpty(x509CertificateChain)) {
                builder.x509CertChain(x509CertificateChain.stream().map(Base64::new).collect(Collectors.toList()));
            }

            String x509SHA1Thumbprint = headers.getX509SHA1Thumbprint();
            if (StringUtils.hasText(x509SHA1Thumbprint)) {
                builder.x509CertThumbprint(new Base64URL(x509SHA1Thumbprint));
            }

            String x509SHA256Thumbprint = headers.getX509SHA256Thumbprint();
            if (StringUtils.hasText(x509SHA256Thumbprint)) {
                builder.x509CertSHA256Thumbprint(new Base64URL(x509SHA256Thumbprint));
            }

            URL x509Uri = headers.getX509Url();
            if (x509Uri != null) {
                try {
                    builder.x509CertURL(x509Uri.toURI());
                } catch (Exception ex) {
                    throw new JwtEncodingException(String.format(ENCODING_ERROR_MESSAGE_TEMPLATE,
                            "Failed to convert '" + JoseHeaderNames.X5U + "' JOSE header to a URI"), ex);
                }
            }

            Map<String, Object> customHeaders = headers.getHeaders().entrySet().stream()
                    .filter((header) -> !JWSHeader.getRegisteredParameterNames().contains(header.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if (!CollectionUtils.isEmpty(customHeaders)) {
                builder.customParams(customHeaders);
            }

            return builder.build();
        }

    }

    private static class JwtClaimsSetConverter implements Converter<JwtClaimsSet, JWTClaimsSet> {

        @Override
        public JWTClaimsSet convert(JwtClaimsSet claims) {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

            URL issuer = claims.getIssuer();
            if (issuer != null) {
                builder.issuer(issuer.toExternalForm());
            }

            String subject = claims.getSubject();
            if (StringUtils.hasText(subject)) {
                builder.subject(subject);
            }

            List<String> audience = claims.getAudience();
            if (!CollectionUtils.isEmpty(audience)) {
                builder.audience(audience);
            }

            Instant issuedAt = claims.getIssuedAt();
            if (issuedAt != null) {
                builder.issueTime(Date.from(issuedAt));
            }

            Instant expiresAt = claims.getExpiresAt();
            if (expiresAt != null) {
                builder.expirationTime(Date.from(expiresAt));
            }

            Instant notBefore = claims.getNotBefore();
            if (notBefore != null) {
                builder.notBeforeTime(Date.from(notBefore));
            }

            String jwtId = claims.getId();
            if (StringUtils.hasText(jwtId)) {
                builder.jwtID(jwtId);
            }

            Map<String, Object> customClaims = claims.getClaims().entrySet().stream()
                    .filter((claim) -> !JWTClaimsSet.getRegisteredNames().contains(claim.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if (!CollectionUtils.isEmpty(customClaims)) {
                customClaims.forEach(builder::claim);
            }

            return builder.build();
        }

    }

}
