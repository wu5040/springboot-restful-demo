package com.example.demo.Mappers;

import com.example.demo.domain.Course;

import java.util.List;
import java.util.Map;

public interface CourseMapper {
    public Course getByKh(String kh);
    public List<Map> getAll();
}
