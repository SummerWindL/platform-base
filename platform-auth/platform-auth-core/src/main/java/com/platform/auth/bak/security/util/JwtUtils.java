package com.platform.auth.bak.security.util;

import com.platform.aix.security.config.JwtConfig;
import com.platform.aix.security.enums.ResultCodeEnum;
import com.platform.aix.security.exception.JwtApiException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Advance
 * @date 2021年12月09日 16:15
 * @since V1.0.0
 */
@Slf4j
public class JwtUtils {
    /**
     * jwt 载荷信息 key
     */
    public static final String JWT_PAYLOAD_USER_KEY = "user";

    /**
     * 刷新token次数 默认为0起始
     */
    public static final String REFRESH_TOKEN_NUMBER = "refreshTokenNumber";

    /**
     * access-token
     */
    public static final String ACCESS_TOKEN = "access-token";

    /**
     * 加密token
     *
     * @param userInfo  载荷中的数据
     * @param jwtConfig jwt 配置
     * @return JWT
     */
    public static String createAccessToken(Object userInfo, JwtConfig jwtConfig) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, userInfo);
        map.put(REFRESH_TOKEN_NUMBER, 0);
        return Jwts.builder()
                .setClaims(map)
                .setId(createJTI())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getAccessTokenSecret())
                .compact();
    }


    /**
     * 生成 RefreshToken
     *
     * @return refreshToken
     */
    public static String createRefreshToken(Object userInfo, JwtConfig jwtConfig) {
        return createRefreshToken(userInfo, jwtConfig, 0);
    }


    /**
     * 生成 RefreshToken
     *
     * @return refreshToken
     */
    public static String createRefreshToken(Object userInfo, JwtConfig jwtConfig, int refreshTokenNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, userInfo);
        map.put(REFRESH_TOKEN_NUMBER, refreshTokenNumber);
        return Jwts.builder()
                .setClaims(map)
                .setId(createJTI())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshTokenExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getRefreshTokenSecret())
                .compact();
    }


    /**
     * 解析 refreshToken
     *
     * @param token     token
     * @param jwtConfig 配置项
     * @return 载荷信息
     */
    public static Claims parserAccessToken(String token, JwtConfig jwtConfig) {
        return parserToken(token, jwtConfig.getAccessTokenSecret());
    }

    /**
     * 解析  token
     *
     * @param token     token
     * @param jwtConfig 配置项
     * @return 载荷信息
     */
    public static Claims parserRefreshToken(String token, JwtConfig jwtConfig) {
        return parserToken(token, jwtConfig.getRefreshTokenSecret());
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token 用户请求中的令牌
     * @return 用户信息
     */
    public static Claims parserToken(String token, String secretKey) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("token{}过期", token, e);
            throw new JwtApiException(ResultCodeEnum.JWT_EXPIRED.code(), ResultCodeEnum.JWT_EXPIRED.message());
        } catch (SignatureException e) {
            log.error("token=[{}], 签名", token, e);
            throw new JwtApiException(ResultCodeEnum.JWT_SIGNATURE.code(), ResultCodeEnum.JWT_SIGNATURE.message());
        } catch (Exception e) {
            log.error("token=[{}]解析错误 message:{}", token, e.getMessage(), e);
            throw new JwtApiException(ResultCodeEnum.JWT_ERROR.code(), ResultCodeEnum.JWT_ERROR.message());
        }
    }


    /**
     * 生成jwt
     * @author Advance
     * @date 2021/12/9 17:35
     * @param username
     * @return java.lang.String
     */
    public static String createJwt(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, username);
        map.put(REFRESH_TOKEN_NUMBER, 0);
        //为了方便测试，我们将过期时间设置为1分钟
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000*60;//过期时间为1分钟
        JwtBuilder builder = Jwts.builder()
                .setId("111")
                .setIssuedAt(new Date())//时间
                .setClaims(map)//添加自定义的数据
                .setSubject("小白")
                .signWith(SignatureAlgorithm.HS256, "itsss")
                .setExpiration(new Date(exp));//方法用于设置过期时间
        System.out.println( builder.compact() );
        return builder.compact();
    }



    private static String createJTI() {
        return new String(java.util.Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }
}
