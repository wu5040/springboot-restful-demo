package com.example.demo.Mappers;

import com.example.demo.domain.Open;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OpenMapper {
    public List<Map> getByGh(String gh);
    public Open getByKhGhXq(String kh,String gh,String xq);
    public List<Map> getAll();
    public List<Map> getelenum();
    /**
     * 查询选了某一课程的所有学生及他们的成绩信息
     */
    public List<Map> getDetail(String kh,String gh,String xq);
    public boolean insert(@Param("xq") String xq, @Param("kh") String kh, @Param("gh") String gh, @Param("sksj") String sksj, @Param("max") Integer max);
    public boolean update(@Param("xq") String xq, @Param("kh") String kh, @Param("gh") String gh, @Param("sksj") String sksj, @Param("max") Integer max);

    public List<Map> search(String inputStr);
}
