package com.example.HrApp.dto.positions;

import com.example.HrApp.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Data
public class GetPositionAppliesDto {

    private Long id;
    private Date created;

    private Date updated;

    private String positionName;

    private int salary;

    private String workingDays;

    private String workingHours;

    private boolean inHoliday;

    private String requirements;



    private List<User> users;
}
