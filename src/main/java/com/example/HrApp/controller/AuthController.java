package com.example.HrApp.controller;

import com.example.HrApp.dto.JwtTokenDto;
import com.example.HrApp.dto.LogInDto;
import com.example.HrApp.dto.RegisterDto;
import com.example.HrApp.entity.Role;
import com.example.HrApp.entity.User;
import com.example.HrApp.exception.UserAlreadyExist;
import com.example.HrApp.repository.RoleRepository;
import com.example.HrApp.repository.UserRepository;
import com.example.HrApp.security.JWTGenerator;
import com.example.HrApp.service.RoleService;
import com.example.HrApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private RoleService roleService;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private UserService userService;
    private RoleRepository roleRepository;
    private JWTGenerator jwtGenerator;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(RoleService roleService, AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService, RoleRepository roleRepository, JWTGenerator jwtGenerator, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        try {
            User user = new User();
            user.setUserName(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            Role role = roleRepository.findByRoleName("USER");
            user.setRole(role);
            userService.Registration(user);
        }catch (UserAlreadyExist exist){
            log.warn("IN register - user with this username is already exist Username: {}", registerDto.getUsername());
            return new ResponseEntity<>(exist.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        log.info("IN register - UserProfile successfully created for user with Username: {} ", registerDto.getUsername());
        return new ResponseEntity<>("User successfully created", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LogInDto dto){
        String token = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtGenerator.generateToken(authentication);
        }catch (Exception e){
            log.info("IN login - Incorrect Username or Password, user with Username: {} ", dto.getUsername());
            return new ResponseEntity<>(new JwtTokenDto(token),HttpStatus.UNAUTHORIZED);
        }


        return new ResponseEntity<>(new JwtTokenDto(token),HttpStatus.OK);

    }

    @GetMapping("roles")
    public ResponseEntity<String> createRoles(){
        Role user = new Role();
        user.setRoleName("USER");
        roleService.createRole(user);

        Role admin = new Role();
        admin.setRoleName("ADMIN");
        roleService.createRole(admin);
        return new ResponseEntity<>("Role successfully created",HttpStatus.OK);
    }
}
