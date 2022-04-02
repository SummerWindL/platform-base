package com.platform.common.util.jwt;

import com.platform.common.exception.JWTTokenException;
import com.platform.common.exception.JWTTokenExpiredException;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

/**
 * JWT Token 帮助类
 *
 * @author : Advance
 * @ClassName : JWTHelper
 * @date : 2017年12月21日 下午2:58:32
 */
public class JWTHelper {

    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param secret
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(JWTInfo jwtInfo, String secret, int expire) throws JWTTokenException {
        return generateToken(jwtInfo, secret.getBytes(), expire);
    }

    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param secret
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(JWTInfo jwtInfo, byte secret[], int expire) throws JWTTokenException {
        try {
            String compactJws = Jwts.builder()
                    .setSubject(jwtInfo.getId())
                    .claim(JWTConstants.JWT_KEY_NAME, jwtInfo.getName())
                    .claim(JWTConstants.JWT_KEY_ALIAS, jwtInfo.getAlias())
                    .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
            return compactJws;
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWTTokenException("[生成token发生异常]", e);
        }

    }

    /**
     * 密钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String secret)
            throws JWTTokenExpiredException, JWTTokenException {
        return parserToken(token, secret.getBytes());
    }

    /**
     * 密钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] sign)
            throws JWTTokenExpiredException, JWTTokenException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(sign).parseClaimsJws(token);
            return claimsJws;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new JWTTokenExpiredException("Token 已过期", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWTTokenException("解析token发生异常", e);
        }
    }

    /**
     * 获取token中的信息
     *
     * @param token
     * @param secret
     * @return
     * @throws Exception
     */
    public static JWTInfo getInfoFromToken(String token, String secret)
            throws JWTTokenExpiredException, JWTTokenException {
        return getInfoFromToken(token, secret.getBytes());
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param secret
     * @return
     * @throws Exception
     */
    public static JWTInfo getInfoFromToken(String token, byte[] secret)
            throws JWTTokenExpiredException, JWTTokenException {
        Jws<Claims> claimsJws = parserToken(token, secret);
        Claims body = claimsJws.getBody();
        String id = body.getSubject();
        String name = String.valueOf(body.get(JWTConstants.JWT_KEY_NAME));
        String alias = String.valueOf(body.get(JWTConstants.JWT_KEY_ALIAS));
        JWTInfo jwtInfo = new JWTInfo(id, name, alias);
        return jwtInfo;
    }

    public static void main(String[] args) throws Exception {
        JWTInfo info = new JWTInfo("254648", "竹信", "的发送到");
        String secret = "ikinloop#df";
        int expire = 100;
        String token = generateToken(info, secret, expire);
        System.out.println(token);

        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNTQ2NDgiLCJqd3RfbmFtZSI6IuerueS_oSIsImp3dF9hbGlhcyI6IueahOWPkemAgeWIsCIsImV4cCI6MTUxMzkwNDczNX0.URm6SkOGTjTrQhDr5dxp-SUU38cnYtc4Fdfi9b5_VVo";

        info = JWTHelper.getInfoFromToken(token, secret);

        System.out.println(info.getName());

    }

}
