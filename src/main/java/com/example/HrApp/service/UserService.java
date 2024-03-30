package com.example.HrApp.service;

import com.example.HrApp.dto.EditUserProfileDto;
import com.example.HrApp.dto.GetUserDto;
import com.example.HrApp.entity.Role;
import com.example.HrApp.entity.Skill;
import com.example.HrApp.entity.User;
import com.example.HrApp.exception.UserAlreadyExist;
import com.example.HrApp.exception.UserNotFoundExceptionById;
import com.example.HrApp.repository.RoleRepository;
import com.example.HrApp.repository.UserRepository;
import com.example.HrApp.service.abstraction.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User Registration(User user) throws UserAlreadyExist {
        if (userRepository.findByUserName(user.getUserName()) != null){
            throw new UserAlreadyExist("This username is used by another user");
        }

        Role role = roleRepository.findByRoleName("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(role);

        User registredUser = userRepository.save(user);
        log.info("IN register - user : {} successfully registered.", registredUser.getUserName());
        return registredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll() - {} users found",result.size());
        return result;
    }

    @Override
    public List<GetUserDto> getAllDto() {
        List<User> result = userRepository.findAll();
        List<GetUserDto> dtos = new ArrayList<>();
        for (User user : result){
            GetUserDto dto = new GetUserDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setAddress(user.getAddress());
            dto.setGender(user.getGender());
            dto.setFinCode(user.getFinCode());
            dto.setBirthday(user.getBirthday());
            dto.setCreated(user.getCreated());
            dto.setUserName(user.getUserName());
            dto.setFirstName(user.getFirstName());
            dto.setUserLastName(user.getUserLastName());
            dto.setUpdated(user.getUpdated());
            dtos.add(dto);
        }
        log.info("IN getAllDto() - {} users found",dtos.size());
        return dtos;
    }

    @Override
    public GetUserDto findDtoById(Long id) throws UserNotFoundExceptionById {
        User result = userRepository.findById(id).orElse(null);
        if (result == null){
            log.warn("IN findDtoById - no found user by id {}", id);
            throw new UserNotFoundExceptionById("Can't find user with id:");
        }
        log.info("IN findDtoById - user: {} found by id: {}", id);
        GetUserDto dto = new GetUserDto();
        dto.setId(result.getId());
        dto.setEmail(result.getEmail());
        dto.setAddress(result.getAddress());
        dto.setGender(result.getGender());
        dto.setFinCode(result.getFinCode());
        dto.setBirthday(result.getBirthday());
        dto.setCreated(result.getCreated());
        dto.setUserName(result.getUserName());
        dto.setFirstName(result.getFirstName());
        dto.setUserLastName(result.getUserLastName());
        dto.setUpdated(result.getUpdated());

        dto.setRole(result.getRole());
        dto.setSkills(result.getSkills());

        return dto;
    }

    @Override
    public User findById(Long id) throws UserNotFoundExceptionById {
        User result = userRepository.findById(id).orElse(null);
        if (result == null){
            log.warn("IN findById - no found user by id {}", id);
            throw new UserNotFoundExceptionById("Not found user with id:" + id);

        }
        log.info("IN findById - user: {} found by id: {}",result.getUserName(), id);


        return result;
    }

    @Override
    public User findByUsername(String name) {
        User result = userRepository.findByUserName(name);
        log.info("IN findByUsername - user: {} found by user: {}", result, name);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted",id);
    }

    @Override
    public void AddSkill(Long userId ,Skill skill) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            user.getSkills().add(skill);

            userRepository.save(user);
        }catch (Exception e){
            log.info(e.getMessage());
        }

    }

    @Override
    public User updateUser(Long userId, EditUserProfileDto updatedUserData) {
        User user = userRepository.findById(userId).orElseThrow(() ->new UsernameNotFoundException("User not found with id:" + userId.toString()));
        user.setFirstName(updatedUserData.getFirstName());
        user.setUserLastName(updatedUserData.getUserLastName());
        user.setGender(updatedUserData.getGender());
        user.setAddress(updatedUserData.getAddress());
        user.setBirthday(updatedUserData.getBirthday());
        user.setEmail(updatedUserData.getEmail());
        user.setFinCode(updatedUserData.getFinCode());
        user.setPhone(updatedUserData.getPhone());

        userRepository.save(user);
        return user;

    }

    @Override
    public List<GetUserDto> searchByUsername(String keyword) {
        List<User> result = userRepository.findByUsernameContaining(keyword);
        List<GetUserDto> dtos = new ArrayList<>();
        for (User user : result){
            GetUserDto dto = new GetUserDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setAddress(user.getAddress());
            dto.setGender(user.getGender());
            dto.setFinCode(user.getFinCode());
            dto.setBirthday(user.getBirthday());
            dto.setCreated(user.getCreated());
            dto.setUserName(user.getUserName());
            dto.setFirstName(user.getFirstName());
            dto.setUserLastName(user.getUserLastName());
            dto.setUpdated(user.getUpdated());
            dtos.add(dto);
        }
        log.info("IN getAllDto() - {} users found",dtos.size());
        return dtos;
    }
}
