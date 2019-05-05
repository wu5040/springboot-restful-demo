package com.example.demo.Mappers;

import com.example.demo.domain.Elective;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ElectiveMapper {
    public List<Map> getByXh(String xh);
    public Elective getByXhXqKhGh(String xh,String xq,String kh,String gh);
    public List<Map> getGrades(String xh);
    public boolean insert(String xh, String xq, String kh, String gh);
    public boolean delete(String xh,String xq,String kh,String gh);
    public boolean update(@Param("xh")String xh, @Param("xq") String xq, @Param("kh") String kh, @Param("gh") String gh, @Param("pscj") double pscj, @Param("kscj") double kscj, @Param("zpcj") double zpcj);
}
