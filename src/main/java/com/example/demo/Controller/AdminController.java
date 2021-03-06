package com.example.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Mappers.CourseMapper;
import com.example.demo.Mappers.OpenMapper;
import com.example.demo.Mappers.StudentMapper;
import com.example.demo.Mappers.TeacherMapper;
import com.example.demo.Status.Exception.NotFoundException;
import com.example.demo.Status.Exception.UnauthorizedException;
import com.example.demo.Status.Result;
import com.example.demo.annotation.CheckToken;
import com.example.demo.annotation.CurrentUser;
import com.example.demo.domain.Course;
import com.example.demo.domain.Open;
import com.example.demo.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        sqlSessionFactory = com.example.springboot.demo.singleton.SingletonMybatis.getSqlSessionFactory();
    }

    @CheckToken
    @RequestMapping(method = RequestMethod.GET, value = "/course")
    public Object getCourse(@CurrentUser User user) throws UnauthorizedException {
        /**
         * 管理员查看所有课程信息
         */
        if (!"admin".equals(user.getRole())) {
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);

        List<Map> courseList = courseMapper.getAll();

        sqlSession.close();

        return new Result("查询课程成功。", Result.StatusCode.SUCCESS.getCode(), null, courseList);
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.POST, value = "/course")
    public Object addCourse(@CurrentUser User user, String kh, String km, Integer xf, Integer xs, double cjRatio, String yxh) throws UnauthorizedException {
        if (!"admin".equals(user.getRole())) {
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);

        Course course=courseMapper.getByKh(kh);
        if(course!=null){
            return new Result("课号已存在。",Result.StatusCode.FAIL.getCode());
        }

        boolean suc = courseMapper.insert(kh, km, xf, xs, cjRatio, yxh);
        if (suc) {
            sqlSession.commit();
            sqlSession.close();
            return new Result("新增课程成功。", Result.StatusCode.SUCCESS.getCode());
        } else {
            sqlSession.close();
            return new Result("新增课程失败。", Result.StatusCode.FAIL.getCode());
        }
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.GET, value = "/open")
    public Object getOpen(@CurrentUser User user) throws UnauthorizedException {
        /**
         * 管理员查看所有开课信息
         */
        if (!"admin".equals(user.getRole())) {
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }


        SqlSession sqlSession = sqlSessionFactory.openSession();
        OpenMapper openMapper = sqlSession.getMapper(OpenMapper.class);

        List<Map> openList = openMapper.getAll();
//        List<Map> elecnum =openMapper.getelenum();
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("elenum", elecnum);

        sqlSession.close();

        return new Result("新增开课信息成功", Result.StatusCode.SUCCESS.getCode(), null, openList);
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.GET, value = "/student")
    public Object getStudent(@CurrentUser User user) throws UnauthorizedException {
        if (!"admin".equals(user.getRole())) {
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        List<Map> stuList = studentMapper.getAll();
        sqlSession.close();
        return new Result("查询学生成功。", Result.StatusCode.SUCCESS.getCode(), null, stuList);
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.GET, value = "/teacher")
    public Object getTeacher(@CurrentUser User user) throws UnauthorizedException {
        if (!"admin".equals(user.getRole())) {
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);

        List<Map> teaList = teacherMapper.getAll();
        sqlSession.close();
        return new Result("查询教师成功。", Result.StatusCode.SUCCESS.getCode(), null, teaList);
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.POST, value = "/open")
    public Object addOpen(@CurrentUser User user, String kh, String gh, String xq, String sksj, Integer max) throws NotFoundException, UnauthorizedException {
        /**
         * 管理员新增开课信息
         * 检查课程是否存在于Course表中
         */
        if (!"admin".equals(user.getRole())) {
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        OpenMapper openMapper = sqlSession.getMapper(OpenMapper.class);

        Course course = courseMapper.getByKh(kh);
        if (course == null) {
            sqlSession.close();
            throw new NotFoundException("课程不存在。", Result.StatusCode.COURSE_NOT_FOUND.getCode());
        }

        Open open = openMapper.getByKhGhXq(kh, gh, xq);
        if (open != null) {
            sqlSession.close();
            return new Result("所开课程已存在。", Result.StatusCode.COURSE_ALREADY_EXIST.getCode());
        }

        boolean suc = openMapper.insert(xq, kh, gh, sksj, max);

        if (suc) {
            sqlSession.commit();
            sqlSession.close();
            return new Result("新增开课信息成功", Result.StatusCode.SUCCESS.getCode());
        } else {
            sqlSession.close();
            return new Result("新增课程失败。", Result.StatusCode.FAIL.getCode());
        }
    }


    @CheckToken
    @RequestMapping(method = RequestMethod.PUT, value = "/open")
    public Object updateOpen(@CurrentUser User user, String kh, String gh, String xq, String sksj, Integer max) throws UnauthorizedException {
        /**
         * 管理员修改开课信息：上课时间和额定人数
         */

        if (!"admin".equals(user.getRole())) {
            throw new UnauthorizedException("role权限错误", Result.StatusCode.Unauthorized.getCode());
        }


        SqlSession sqlSession = sqlSessionFactory.openSession();
        OpenMapper openMapper = sqlSession.getMapper(OpenMapper.class);
        boolean suc = openMapper.update(xq, kh, gh, sksj, max);

        if (suc) {
            sqlSession.commit();
            sqlSession.close();
            return new Result("修改开课信息成功。", Result.StatusCode.SUCCESS.getCode());
        } else {
            sqlSession.close();
            return new Result("修改开课信息失败。", Result.StatusCode.FAIL.getCode());
        }

    }

}
