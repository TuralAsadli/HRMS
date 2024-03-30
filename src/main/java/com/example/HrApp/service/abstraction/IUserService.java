package com.example.HrApp.service.abstraction;

import com.example.HrApp.dto.EditUserProfileDto;
import com.example.HrApp.dto.GetUserDto;
import com.example.HrApp.entity.Skill;
import com.example.HrApp.entity.User;
import com.example.HrApp.exception.UserAlreadyExist;
import com.example.HrApp.exception.UserNotFoundExceptionById;

import java.util.List;

public interface IUserService {
    User Registration(User user) throws UserAlreadyExist;
    List<User> getAll();
    List<GetUserDto> getAllDto();
    GetUserDto findDtoById(Long id) throws UserNotFoundExceptionById;
    User findById(Long id) throws UserNotFoundExceptionById;
    User findByUsername(String Name);
    void delete(Long id);

    void AddSkill(Long userId,Skill skill);
    User updateUser(Long userId, EditUserProfileDto updatedUserData);

    List<GetUserDto> searchByUsername(String keyword);

}
