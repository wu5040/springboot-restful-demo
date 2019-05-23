package com.example.demo.Mappers;

import com.example.demo.domain.Student;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {
    public List<Map> getAll();

    public Student getBySno(String sno);

    public boolean insert(String sno);

    public boolean update(String sno, String xm, String xb, Date csrq, String jg, String sjhm, String yxh);
}
