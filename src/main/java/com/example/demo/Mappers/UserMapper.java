package com.example.demo.Mappers;

import com.example.demo.domain.User;

import java.util.List;

public interface UserMapper {
    public User getByUserId(String userId);
    public List<User> getUsers();
    public boolean insert(String userId,String password);
    public boolean insert1(String userId,String password,String name);
    public boolean updateUser(User user);
}
