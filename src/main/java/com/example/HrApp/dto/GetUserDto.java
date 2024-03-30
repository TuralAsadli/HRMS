package com.example.HrApp.dto;

import com.example.HrApp.entity.Role;
import com.example.HrApp.entity.Skill;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Data
public class GetUserDto {

    private Long id;

    private Date created;

    private Date updated;

    private String userName;
    private String firstName;
    private String userLastName;
    private String gender;
    private String email;
    private String phone;
    private Date birthday;
    private String address;
    private String finCode;


    private Role role;


    private List<Skill> skills;
}
