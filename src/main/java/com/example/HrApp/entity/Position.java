package com.example.HrApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Position {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @Column(name = "created")
    private Date created;
    @LastModifiedDate
    @Column(name = "updated")
    private Date updated;
    @Column(name = "position_name")
    private String positionName;
    @Column(name = "salary")
    private int salary;
    @Column(name = "working_days")
    private String workingDays;
    @Column(name = "working_hours")
    private String workingHours;
    @Column(name = "in_holiday")
    private boolean inHoliday;
    @Column(name = "requirements")
    private String requirements;


    @ManyToMany(mappedBy = "positions")
    private List<User> users;
}
