package com.blog.blogappapis.services;

import java.util.List;

import com.blog.blogappapis.payloads.UserDto;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUser(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(int id);
    
}
