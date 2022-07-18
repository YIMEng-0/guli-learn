package com.luobin.common_utils;

/*************************
 *@author : YIMENG
 *@date : 2022/6/30 11:05
 *************************/

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 下面这个类主要有下面的功能：
 * 1、生成 token 的策略配置
 * 2、两种方式判断 token 是不是有效的
 * 3、根据生成的 token 取出来相关的用户信息
 */
public class JwtUtils {
    /**
     * 设置 token 的过期时间
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    /**
     * 秘钥，按照一定规则生成，用来加密
     */
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成 token 字符串的方法
     * @param id 用户 id
     * @param nickname 用户名称
     * @return
     */
    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder() // 构建 token 字符串
                .setHeaderParam("typ", "JWT")    // 固定的写法，用来生成 JWT 头
                .setHeaderParam("alg", "HS256")  // 固定的写法，用来生成 JWT 头

                .setSubject("guli-user")              // 分类，名字随意的
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))  // 过期时间 = 当前时间 + 过期时间间隔

                .claim("id", id)
                .claim("nickname", nickname)        // 设置 token 的主体部分， 可以存储用户信息，这里写了用户 id 以及用户名字
                                                       // 存在多个信息的话，可以使用 .claim 进行添加即可

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();                            // 签名哈希：使用什么样子的编码方式，根据自己设定的秘钥进行 token 的生成
        return JwtToken;
    }

    /**
     * 判断 token 是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) return false;
        try {
            // 根据自己执行的 秘钥 判断 token 是不是有效的。有效才能放进去访问服务
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断 token 是否存在与有效
     *
     * 使用的是 HttpServletRequest 将 token 取出来，然后做一个判断
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 根据 token 获取用户 id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();

        return (String)claims.get("id");
    }
}