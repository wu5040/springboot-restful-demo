package com.example.demo.Mappers;

import com.example.demo.domain.Teacher;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    public List<Map> getAll();
    public Teacher getByGh(String gh);
    public boolean insert(String gh);
    public boolean update(String gh, String xm, String xb, Date csrq, String xl, String jbgz, String yxh);
}
