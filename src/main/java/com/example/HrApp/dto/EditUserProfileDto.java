package com.example.HrApp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EditUserProfileDto {
    private Long userId;
    private String firstName;
    private String userLastName;
    private String gender;
    private String email;
    private String phone;
    private Date birthday;
    private String address;
    private String finCode;
}
