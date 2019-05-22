package com.example.demo.Controller;

import com.example.demo.Mappers.CourseMapper;
import com.example.demo.Mappers.ElectiveMapper;
import com.example.demo.Mappers.OpenMapper;
import com.example.demo.Status.Exception.NotFoundException;
import com.example.demo.Status.Exception.UnauthorizedException;
import com.example.demo.Status.Result;
import com.example.demo.annotation.CheckToken;
import com.example.demo.annotation.CurrentUser;
import com.example.demo.domain.Course;
import com.example.demo.domain.Open;
import com.example.demo.domain.Student;
import com.example.demo.domain.User;
//import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        sqlSessionFactory = com.example.springboot.demo.singleton.SingletonMybatis.getSqlSessionFactory();
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.GET,value = "/open")
    public Object getOpen(@CurrentUser User user) throws UnauthorizedException{
        /**
         * 教师查看自己的开课信息api
         */

        if(!"teacher".equals(user.getRole())){

            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession=sqlSessionFactory.openSession();
        OpenMapper openMapper=sqlSession.getMapper(OpenMapper.class);

        List<Map> openList=openMapper.getByGh(user.getUserId());

        sqlSession.close();
        return new Result("查询开课信息成功",Result.StatusCode.SUCCESS.getCode(),null,openList);
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.GET,value = "/open/detail")
    public Object getOpenDetail(@CurrentUser User user,String kh,String xq) throws NotFoundException,UnauthorizedException{
        /**
         * 教师查看某门课程的详细信息，包括选择这门课的学生及其成绩
         */
        if(!"teacher".equals(user.getRole())){
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession=sqlSessionFactory.openSession();
        OpenMapper openMapper=sqlSession.getMapper(OpenMapper.class);

        List<Map> OpenDetail=openMapper.getDetail(kh,user.getUserId(),xq);
        sqlSession.close();
        if(!OpenDetail.isEmpty()){
            return new Result("查询课程信息成功。",Result.StatusCode.SUCCESS.getCode(),null,OpenDetail);
        }else {
            throw new NotFoundException("查询失败，没有该门课的信息。",Result.StatusCode.COURSE_NOT_FOUND.getCode());
        }
    }

    @CheckToken
    @RequestMapping(method = RequestMethod.POST,value = "/open/detail")
    public Object updateScore(@CurrentUser User user,String kh,String xq,String xh,double pscj,double kscj) throws NotFoundException,UnauthorizedException{
        /**
         * 教师修改自己所授课程的学生成绩，只需给出平时成绩和考试成绩，自动根据占比算出总评成绩
         */
        if(!"teacher".equals(user.getRole())){
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession=sqlSessionFactory.openSession();
        CourseMapper courseMapper=sqlSession.getMapper(CourseMapper.class);
        ElectiveMapper electiveMapper=sqlSession.getMapper(ElectiveMapper.class);

        Course course=courseMapper.getByKh(kh);
        if(course==null){
            sqlSession.close();
            throw new NotFoundException("课程不存在。",Result.StatusCode.COURSE_NOT_FOUND.getCode());
        }

        double cjRatio=course.getCjRatio();
        System.out.println(cjRatio);

        double zpcj=pscj*cjRatio+kscj*(1-cjRatio);
        System.out.println(zpcj);

        electiveMapper.update(xh,xq,kh,user.getUserId(),pscj,kscj,zpcj);
        sqlSession.commit();
        sqlSession.close();
        return new Result("修改成功。",Result.StatusCode.SUCCESS.getCode());
    }
}
