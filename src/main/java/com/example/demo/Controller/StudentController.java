package com.example.demo.Controller;


import com.example.demo.Mappers.ElectiveMapper;
import com.example.demo.Mappers.OpenMapper;
import com.example.demo.Status.Exception.NotFoundException;
import com.example.demo.Status.Exception.UnauthorizedException;
import com.example.demo.Status.Result;
import com.example.demo.annotation.CheckToken;
import com.example.demo.annotation.CurrentUser;
import com.example.demo.domain.Elective;
import com.example.demo.domain.Open;
import com.example.demo.domain.User;
//import org.apache.el.lang.ELArithmetic;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {


    private static SqlSessionFactory sqlSessionFactory;

    static {
        sqlSessionFactory = com.example.springboot.demo.singleton.SingletonMybatis.getSqlSessionFactory();
    }


    @CheckToken
    @RequestMapping  //如果方法上的RequestMapping没有value，则此方法默认被父路径调用
    public String index(@CurrentUser User user) {
        if ("student".equals(user.getRole())){
            return "hello student";
        }
        else {
            return "you are not a student!";
        }

    }

    @CheckToken
    @RequestMapping(method = RequestMethod.GET,value = "/elective")
    public Object getCourseSelected(@CurrentUser User user) throws UnauthorizedException {
        /**
         *  学生查看本学期已选的全部课程api
         */
        if(!"student".equals(user.getRole())){
            throw new UnauthorizedException("role权限错误",Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        ElectiveMapper electiveMapper=sqlSession.getMapper(ElectiveMapper.class);
        sqlSession.close();
        List<Map> electives = electiveMapper.getByXh(user.getUserId());

        return new Result("查询已选课程成功",Result.StatusCode.SUCCESS.getCode(),null,electives);
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.POST,value = "/elective")
    public Object electiveCourse(@CurrentUser User user, String kh, String gh, String xq) throws NotFoundException,UnauthorizedException {
        /**
            学生选课api，
            通过课号kh，教师工号gh，学期xq在Open开课表中选择课程
            写入Elective选课表
            检查所选课程是否存在于Open开课表中
            检查是否已选择该课程

         */

        if(!"student".equals(user.getRole())){
            throw new UnauthorizedException("role权限错误",Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        OpenMapper openMapper=sqlSession.getMapper(OpenMapper.class);
        ElectiveMapper electiveMapper=sqlSession.getMapper(ElectiveMapper.class);

        //查询在开课表Open中是否存在该课程
        Open open=openMapper.getByKhGhXq(kh,gh,xq);

        if(open==null){ //不存在该课程
            sqlSession.close();
            throw new NotFoundException("选课失败，该课程不存在。",Result.StatusCode.COURSE_NOT_FOUND.getCode());
        }else {         //存在该课程
            if(electiveMapper.getByXhXqKhGh(user.getUserId(),xq,kh,gh)==null){
                electiveMapper.insert(user.getUserId(), xq, kh, gh);   //在Elective表中插入记录，选课成功
                sqlSession.commit();
                sqlSession.close();
                return new Result("选课成功", Result.StatusCode.SUCCESS.getCode());
            }else {
                sqlSession.close();
                return new Result("该课程已选。",Result.StatusCode.COURSE_ALREADY_EXIST.getCode());
            }
        }

    }


    @CheckToken
    @RequestMapping(method = RequestMethod.DELETE,value = "/elective")
    public Object delElective(@CurrentUser User user,String kh,String gh,String xq) throws NotFoundException,UnauthorizedException{
        /**
         * 学生删除已选课程api
         * 检查选课表Elective是否存在该课程
         */

        if(!"student".equals(user.getRole())){
            throw new UnauthorizedException("role权限错误",Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        ElectiveMapper electiveMapper=sqlSession.getMapper(ElectiveMapper.class);

        Elective elective=electiveMapper.getByXhXqKhGh(user.getUserId(),xq,kh,gh);

        if(elective==null){
            sqlSession.close();
            throw new NotFoundException("所删除课程不存在。",Result.StatusCode.COURSE_NOT_FOUND.getCode());
        }

        electiveMapper.delete(user.getUserId(),xq,kh,gh);   //在Elective表中删除记录
        sqlSession.commit();
        sqlSession.close();
        return new Result("删除课程成功。",Result.StatusCode.SUCCESS.getCode());

    }

    @CheckToken
    @RequestMapping(method = RequestMethod.GET,value = "/elective/grades")
    public Object getGrades(@CurrentUser User user) throws UnauthorizedException{
        /**
         * 学生查询课程成绩api
         */
        if(!"student".equals(user.getRole())){
            throw new UnauthorizedException("role权限错误",Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        ElectiveMapper electiveMapper=sqlSession.getMapper(ElectiveMapper.class);

        List<Map> grades = electiveMapper.getGrades(user.getUserId());

        return new Result("查询成绩成功。", Result.StatusCode.SUCCESS.getCode(),null,grades);

    }

}
