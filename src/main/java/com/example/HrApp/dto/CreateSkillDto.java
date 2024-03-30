package com.example.HrApp.dto;

import com.example.HrApp.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@Builder
public class CreateSkillDto {

    private Long userId;
    private String skillName;
    private String skillLevel;

}
