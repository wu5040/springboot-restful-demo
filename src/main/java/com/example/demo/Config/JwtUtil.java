package com.example.demo.Config;
import io.jsonwebtoken.Claims;
import com.example.demo.domain.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class JwtUtil {
    /**
     * JwtUtil工具类：生成token,解析token,校验token
     */


    //过期时间
    private static final long EXPIRE_TIME=15*60*1000;

    public static String createJWT(User user){
        //指定签名算法
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //创建payload的私有声明
        Map<String,Object> claims = new HashMap<String, Object>();
        claims.put("sno",user.getSno());
        claims.put("password",user.getPassword());

        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String key = user.getPassword();

        //生成签发人
        String subject = user.getSno();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm,key)
                .setExpiration(new Date(nowMillis+EXPIRE_TIME));

        return builder.compact();
    }


    /**
     * Token的解密
     */
    public static Claims parseJWT(String token,User user){
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = user.getPassword();

        Claims claims=Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();

        return claims;
    }

    /**
     * Token的校验
     */
    public static Boolean isVerify(String token,User user){
        String key=user.getPassword();

        Claims claims=Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();

        if(claims.get("password").equals(user.getPassword())){
            return true;
        }
        return false;
    }
}
