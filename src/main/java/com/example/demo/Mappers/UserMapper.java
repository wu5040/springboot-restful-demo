package com.example.demo.Mappers;

import com.example.demo.domain.User;

import java.util.List;

public interface UserMapper {
    public User getBySno(String sno);
    public List<User> getUsers();
    public boolean insert(String sno,String password);
    public boolean insert1(String sno,String password,String name);
    public boolean updateUser(User user);
}
