package com.example.userservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Chuob Bunthoeurn
 */
@Data
public class UserDTO implements Serializable {
    protected Long id;
    private String username;
    private String name;
    private String profile;
    private String email;
    private String phone;
    private String password;
    private String status;
}
