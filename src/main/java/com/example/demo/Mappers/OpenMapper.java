package com.example.demo.Mappers;

import com.example.demo.domain.Open;

import java.util.List;
import java.util.Map;

public interface OpenMapper {
    public List<Map> getByGh(String gh);
    public Open getByKhGhXq(String kh,String gh,String xq);

    /**
     * 查询选了某一课程的所有学生及他们的成绩信息
     */
    public List<Map> getDetail(String kh,String gh,String xq);
    public boolean insert(String xq, String kh, String gh, String sksj);
    public boolean update(String xq, String kh, String gh, String sksj);

    public List<Map> searchByKm(String km);
}
