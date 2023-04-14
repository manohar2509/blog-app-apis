package com.blog.blogappapis.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4,message = "Username should be atleast 4 characters long !!")
    private String username;
    @NotEmpty
    @Email(message = "Email address is not valid !!")
    private String email;
    @NotEmpty
    @Size(min = 3, max = 10 , message = "Password should be between 3 and 10 characters long !!")
    private String password;
    @NotEmpty
    private String about;
    private Set<RoleDto> roles=new HashSet<>();

}