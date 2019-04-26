package com.example.demo.Mappers;

import com.example.demo.domain.Elective;

import java.util.List;
import java.util.Map;

public interface ElectiveMapper {
    public List<Map> getByXh(String xh);
    public Elective getByXhXqKhGh(String xh,String xq,String kh,String gh);
    public boolean insert(String xh, String xq, String kh, String gh);
    public boolean update(String xh, String xq, String kh, String gh, double pscj, double kscj, double zpcj);
}
