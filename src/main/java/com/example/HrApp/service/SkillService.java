package com.example.HrApp.service;

import com.example.HrApp.dto.CreateSkillDto;
import com.example.HrApp.entity.Skill;
import com.example.HrApp.exception.UserNotFoundExceptionById;
import com.example.HrApp.repository.SkillRepository;
import com.example.HrApp.repository.UserRepository;
import com.example.HrApp.service.abstraction.ISkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService implements ISkillService {

    @Autowired
    private  SkillRepository skillRepository;
    @Autowired
    private UserService userService;


    @Override
    public Skill createSkill(CreateSkillDto dto) throws UserNotFoundExceptionById {

        Skill skill = new Skill();
        skill.setSkillName(dto.getSkillName());
        skill.setSkillLevel(dto.getSkillLevel());
        skill.setUser(userService.findById(dto.getUserId()));

        skillRepository.save(skill);
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findById(Long id) {
        if (skillRepository.findById(id).isPresent()){
            return skillRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Skill findByUsername(String Name) {
        if (skillRepository.findBySkillName(Name).isPresent()){
            return skillRepository.findBySkillName(Name).get();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        skillRepository.deleteById(id);

    }
}
