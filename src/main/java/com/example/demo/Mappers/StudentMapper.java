package com.example.demo.Mappers;

import com.example.demo.domain.Student;

import java.sql.Date;

public interface StudentMapper {
    public Student getBySno(String Sno);

    public boolean insert(String sno, String password, String xm, String xb, Date csrq, String jg, String sjhm, String yxh);

    public boolean update(String sno, String xm, String xb, Date csrq, String jg, String sjhm, String yxh);
}
