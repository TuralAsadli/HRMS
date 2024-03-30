package com.example.HrApp.controller;

import com.example.HrApp.dto.CreateSkillDto;
import com.example.HrApp.dto.EditUserProfileDto;
import com.example.HrApp.dto.GetUserDto;
import com.example.HrApp.entity.User;
import com.example.HrApp.exception.UserNotFoundExceptionById;
import com.example.HrApp.exception.position.NotFoundPositionException;
import com.example.HrApp.repository.UserRepository;
import com.example.HrApp.service.PositionService;
import com.example.HrApp.service.SkillService;
import com.example.HrApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private UserRepository repository;

    @Autowired
    private PositionService positionService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<GetUserDto> GetUser(@PathVariable Long id) throws Exception {
        try {
            GetUserDto user = userService.findDtoById(id);
            log.info("IN GetUser - user with id: {} ",id);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundExceptionById e){
            log.warn("Warn GetUser - user with id: {} not found",id);
            GetUserDto dto = new GetUserDto();
            dto.setId(id);
            return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/skill")
    public ResponseEntity<String> skill(@RequestBody CreateSkillDto dto) {
        try {
            skillService.createSkill(dto);
            log.info("IN skill - skill successfully created for user with id: {} ",dto.getUserId());

        }catch (UserNotFoundExceptionById exception){
            log.warn("IN skill - Not found user with id: {}", dto.getUserId());
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);

        }


        return new ResponseEntity<>("Skill successfully created", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editUserProfile(@RequestBody EditUserProfileDto dto){
        userService.updateUser(dto.getUserId(), dto);
        log.info("IN editUserProfile - UserProfile successfully updated for user with id: {} ",dto.getUserId());
        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);

    }

    @PostMapping("/applyPosition")
    public ResponseEntity<String> applyPosition(@RequestParam Long userId, @RequestParam Long positionId){
        try {
            positionService.addApply(userId,positionId);
            log.info("IN applyPosition - Position successfully applied for user with id: {} ",userId);
            return new ResponseEntity<>("Successfully", HttpStatus.OK);
        }catch (UserNotFoundExceptionById | NotFoundPositionException e){
            log.warn("IN applyPosition - Not found user with id: {}", userId);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/SqlQuery")
    public ResponseEntity<List<User>> queryRequest(){
        List<User> users = repository.findAllUsersWithRole();
        return new ResponseEntity<>(users,HttpStatus.OK);

    }

    @GetMapping("/searchUser")
    public ResponseEntity<List<GetUserDto>> searchUser(@RequestParam String name){
        return new ResponseEntity<>(userService.searchByUsername(name),HttpStatus.OK) ;
    }

}
