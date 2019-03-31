package com.example.demo.Controller;

import com.example.demo.Mappers.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.pwdEncoder.pwdEncoder;
import com.example.springboot.demo.singleton.SingletonMybatis;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index") //在类上使用RequestMapping，里面设置的value就是方法的父路径
public class UserController {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        sqlSessionFactory =  SingletonMybatis.getSqlSessionFactory();
    }
    @RequestMapping  //如果方法上的RequestMapping没有value，则此方法默认被父路径调用
    public String index(){
        return "hello spring boot";
    }

    //这里体现了restful风格的请求，按照请求的类型，来进行增删查改。
    @RequestMapping(method = RequestMethod.GET,value = "/users")
    public List<User> getUsers(){
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
        }finally {
            //最后记得关闭连接
            sqlSession.close();
        }

        return listUsers;
    }
    //这里用的是路径变量，就是{}括起来的，会当做变量读进来
//    @RequestMapping(method = RequestMethod.GET,value = "/users/{userId}")
//    public User getUser(@PathVariable int userId){
//        User user;
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        try {
//            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//            user = userMapper.getById(userId);
//            sqlSession.commit();
//        }finally {
//            sqlSession.close();
//        }
//        return user;
//    }

    //RequestBody这个注解可以接收json数据
    //注册新用户
    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public boolean setUser(String sno,String password){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            String pwdEncoded= pwdEncoder.getSaltMD5(password);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insert(sno,pwdEncoded);
            sqlSession.commit();
            return true;
        }catch (Exception e){
            return false;
        }finally {
            sqlSession.close();
        }
    }

    //登录
    @RequestMapping(method =RequestMethod.POST,value = "/login")
    public boolean login(String sno,String password){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
            User user=userMapper.getBySno(sno);
            String pwd=user.getPassword();
            boolean isSuccess=pwdEncoder.getSaltverifyMD5(password,pwd);
            return  isSuccess;
        } catch (Exception e){
            return false;
        } finally {
            sqlSession.close();
        }
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
