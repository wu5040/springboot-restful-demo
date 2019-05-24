package com.example.demo.Mappers;

import com.example.demo.domain.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CourseMapper {
    public Course getByKh(String kh);

    public List<Map> getAll();

    public boolean insert(@Param("kh") String kh, @Param("km")String km,
                          @Param("xf")Integer xf, @Param("xs")Integer xs,
                          @Param("cjRatio")double cjRatio, @Param("yxh")String yxh);
}
