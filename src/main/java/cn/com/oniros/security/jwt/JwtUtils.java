package cn.com.oniros.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * cn.com.oniros.security.jwt.JwtUtils
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:08
 */
public class JwtUtils {

    /**
     *  Expire time, 14d.
     */
    public static final Long JWT_EXPIRE_TIME = 60 * 60 * 1000L;

    public static final String SIGN_ALGORITHMS = "AES";

    public static final String JWT_KEY = "jwt";

    /**
     * Create jwt token.
     *
     * @param id salt.
     * @param subject username here.
     * @param expire expire time.
     * @return token.
     */
    public static String createToken(String id, String subject, Long expire, String roleName) {
        JwtBuilder builder = getJwtBuilder(subject, expire, id, roleName);
        return builder.compact();
    }

    /**
     * Create jwt token.
     *
     * @param subject username here.
     * @param expire expire time.
     * @return token.
     */
    public static String createToken(String subject, Long expire, String roleName) {
        JwtBuilder builder = getJwtBuilder(subject, expire, UUID.randomUUID().toString(), roleName);
        return builder.compact();
    }

    /**
     * parse jwt token.
     *
     * @param token jwt token
     * @return parsed claim.
     */
    public static Claims parseToken(String token) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long expire, String uuid, String roleName) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        Date date = new Date();
        if (expire == null) {
            expire = JWT_EXPIRE_TIME;
        }
        if (uuid == null) {
            uuid = getUUID();
        }
        long expireTime = date.getTime() + expire;
        Date expireDate = new Date(expireTime);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer(JWT_KEY)
                .setIssuedAt(date)
                .setAudience(roleName)
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expireDate);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, SIGN_ALGORITHMS);
    }

    public static String generateJwtCacheKey(String username) {
        return JWT_KEY + ":" + username;
    }

}
