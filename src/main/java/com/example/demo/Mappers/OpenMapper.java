package com.example.demo.Mappers;

import com.example.demo.domain.Open;

public interface OpenMapper {
    public Open getByKhGhXq(String kh,String gh,String xq);
    public boolean insert(String xq, String kh, String gh, String sksj);
    public boolean update(String xq, String kh, String gh, String sksj);
}
