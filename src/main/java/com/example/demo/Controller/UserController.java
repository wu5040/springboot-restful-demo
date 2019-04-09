package com.example.demo.Controller;

import com.example.demo.Mappers.UserMapper;
import com.example.demo.Status.Exception.NotFoundException;
import com.example.demo.Status.Exception.UnauthorizedException;
import com.example.demo.Util.CheckToken;
import com.example.demo.Util.LoginToken;
import com.example.demo.domain.User;
import com.example.demo.Util.JwtUtil;
import com.example.demo.Util.pwdEncoder;
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

    @Autowired
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
    public Object setUser(String sno, String password) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.getBySno(sno);

        if (user != null) {
            sqlSession.close();
            return new Result("用户名已存在", Result.StatusCode.FAIL.getCode());
        } else {
            String pwdEncoded = pwdEncoder.getSaltMD5(password);
            userMapper.insert(sno, pwdEncoded);
            sqlSession.commit();
            sqlSession.close();
            return new Result("注册成功", Result.StatusCode.SUCCESS.getCode());
        }
    }

    //登录
    @LoginToken
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public Object login(String sno, String password) throws NotFoundException, UnauthorizedException {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getBySno(sno);
        sqlSession.close();
        if (user == null) {
            throw new NotFoundException("用户名不存在", Result.StatusCode.USER_NOT_FOUND.getCode());
        } else {
            String pwd = user.getPassword();
            boolean isSuccess = pwdEncoder.getSaltverifyMD5(password, pwd);
            if (isSuccess) {
                String token = JwtUtil.createJWT(user);
                JSONObject userInfo=new JSONObject();
                userInfo.put("sno",user.getSno());
                userInfo.put("name",user.getName());
                userInfo.put("token",token);
                return new Result("登录成功", Result.StatusCode.SUCCESS.getCode(),userInfo);
            } else {
                throw new UnauthorizedException("密码错误", Result.StatusCode.Unauthorized.getCode());
            }
        }
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.GET,value = "/testToken")
    public String testToken(){
        return "通过token验证";
    }
    //修改信息
//    @RequestMapping(method = RequestMethod.PUT,value = "/user/{userid}/{name}")
//    public boolean updateUser(@PathVariable int userid,@PathVariable String name){
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        try {
//            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//            User user = new User(userid,name);
//            userMapper.updateUser(user);
//            sqlSession.commit();
//        }finally {
//            sqlSession.close();
//        }
//        return true;
//    }

}
