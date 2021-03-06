package com.example.demo.Controller;

import com.example.demo.Config.JwtUtil;
import com.example.demo.Config.pwdEncoder;
import com.example.demo.Mappers.StudentMapper;
import com.example.demo.Mappers.TeacherMapper;
import com.example.demo.Mappers.UserMapper;
import com.example.demo.Status.Exception.NotFoundException;
import com.example.demo.Status.Exception.UnauthorizedException;
import com.example.demo.annotation.*;
import com.example.demo.domain.User;
import com.example.demo.Status.Result;
import com.example.springboot.demo.singleton.SingletonMybatis;

import com.alibaba.fastjson.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index") //在类上使用RequestMapping，里面设置的value就是方法的父路径
public class UserController {

    private static SqlSessionFactory sqlSessionFactory;


    static {
        sqlSessionFactory = SingletonMybatis.getSqlSessionFactory();
    }

    @RequestMapping  //如果方法上的RequestMapping没有value，则此方法默认被父路径调用
    public String index() {
        return "hello spring boot";
    }

    //这里体现了restful风格的请求，按照请求的类型，来进行增删查改。
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getUsers() {
        List<User> listUsers;
        //获取一个连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //得到映射器
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用接口中的方法去执行xml文件中的SQL语句
            listUsers = userMapper.getUsers();
            //要提交后才会生效
            sqlSession.commit();
        } finally {
            //最后记得关闭连接
            sqlSession.close();
        }

        return listUsers;
    }

    //RequestBody这个注解可以接收json数据
    //注册新用户
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public Object setUser(String userId, String password,String role) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        StudentMapper studentMapper =sqlSession.getMapper(StudentMapper.class);
        TeacherMapper teacherMapper =sqlSession.getMapper(TeacherMapper.class);

        User user = userMapper.getByUserId(userId);

        if (user != null) {
            sqlSession.close();
            return new Result("用户名已存在", Result.StatusCode.FAIL.getCode());
        } else {
            String pwdEncoded = pwdEncoder.getSaltMD5(password);
            userMapper.insert(userId, pwdEncoded,role);

            if(role.equals("student")){
                System.out.println("aaa");
                studentMapper.insert(userId);
            }else if(role.equals("teacher")){
                teacherMapper.insert(userId);
            }

            sqlSession.commit();
            sqlSession.close();
            return new Result("注册成功", Result.StatusCode.SUCCESS.getCode());
        }
    }

    //登录
    @LoginToken
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public Object login(String userId, String password) throws NotFoundException, UnauthorizedException {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getByUserId(userId);

        sqlSession.close();
        if (user == null) {
            throw new NotFoundException("用户名不存在", Result.StatusCode.USER_NOT_FOUND.getCode());
        } else {
            String pwd = user.getPassword();
            boolean isSuccess = pwdEncoder.getSaltverifyMD5(password, pwd);
            if (isSuccess) {
                String token = JwtUtil.createJWT(user);
                JSONObject userInfo = new JSONObject();
                userInfo.put("userId", user.getUserId());
                userInfo.put("name", user.getName());
                userInfo.put("role",user.getRole());
                userInfo.put("token", token);

                return new Result("登录成功", Result.StatusCode.SUCCESS.getCode(), userInfo,null);
            } else {
                throw new UnauthorizedException("密码错误", Result.StatusCode.Unauthorized.getCode());
            }
        }
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.POST, value = "/testToken")
    public Object testToken(@CurrentUser User user) {
        return user;
    }

}
