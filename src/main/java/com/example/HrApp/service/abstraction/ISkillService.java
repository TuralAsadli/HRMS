package com.example.HrApp.service.abstraction;


import com.example.HrApp.dto.CreateSkillDto;
import com.example.HrApp.entity.Skill;
import com.example.HrApp.exception.UserNotFoundExceptionById;

import java.util.List;

public interface ISkillService {
    Skill createSkill(CreateSkillDto skill) throws UserNotFoundExceptionById;
    List<Skill> getAll();
    Skill findById(Long id);
    Skill findByUsername(String Name);
    void delete(Long id);
}
