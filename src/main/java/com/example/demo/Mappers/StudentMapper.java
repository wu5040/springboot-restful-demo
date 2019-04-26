package com.example.demo.Mappers;

import com.example.demo.domain.Student;

import java.sql.Date;

public interface StudentMapper {
    public Student getBySno(String sno);

    public boolean insert(String sno);

    public boolean update(String sno, String xm, String xb, Date csrq, String jg, String sjhm, String yxh);
}
