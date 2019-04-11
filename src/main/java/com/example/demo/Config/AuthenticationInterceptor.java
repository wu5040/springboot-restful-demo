package com.example.demo.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.Mappers.UserMapper;
import com.example.demo.Status.Exception.NotFoundException;
import com.example.demo.Status.Exception.UnauthorizedException;
import com.example.demo.Status.Result;
import com.example.demo.annotation.CheckToken;
import com.example.demo.annotation.LoginToken;
import com.example.demo.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private static SqlSessionFactory sqlSessionFactory;

    private static final String USER="user";

    static {
        sqlSessionFactory = com.example.springboot.demo.singleton.SingletonMybatis.getSqlSessionFactory();
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object object) throws Exception {

        String token =httpServletRequest.getHeader("token");//从请求头获取token
        if(!(object instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod) object;
        Method method=handlerMethod.getMethod();

        //检查是否有LoginToken注释，有则跳过认证
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限CheckToken的注解
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (checkToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new UnauthorizedException("无token，请重新登录", Result.StatusCode.Unauthorized.getCode());
                }
                // 获取 token 中的 user Sno
                String userSno;
                try {
                    userSno = JWT.decode(token).getClaim("sno").asString();
                } catch (JWTDecodeException j) {
                    throw new UnauthorizedException("访问异常！", Result.StatusCode.Unauthorized.getCode());
                }

                SqlSession sqlSession = sqlSessionFactory.openSession();

                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

                User user = userMapper.getBySno(userSno);

                sqlSession.close();

                if (user == null) {
                    throw new NotFoundException("用户名不存在！", Result.StatusCode.USER_NOT_FOUND.getCode());
                }
                Boolean verify = JwtUtil.isVerify(token, user);
                if (!verify) {
                    throw new UnauthorizedException("非法访问！", Result.StatusCode.Unauthorized.getCode());
                }

                //将user 添加到 request中，以便后续操作获取user
                httpServletRequest.setAttribute(USER,user);
                return true;
            }
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
