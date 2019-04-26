package com.example.demo.Mappers;

import com.example.demo.domain.User;

import java.util.List;

public interface UserMapper {
    public User getByUserId(String userId);
    public List<User> getUsers();
    public boolean insert(String userId,String password,String role);
    public boolean updateUser(User user);
}
