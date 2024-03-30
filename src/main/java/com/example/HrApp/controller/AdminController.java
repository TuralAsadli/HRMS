package com.example.HrApp.controller;

import com.example.HrApp.dto.GetUserDto;
import com.example.HrApp.dto.positions.GetPositionAppliesDto;
import com.example.HrApp.entity.Position;
import com.example.HrApp.exception.position.NotFoundPositionException;
import com.example.HrApp.service.PositionService;
import com.example.HrApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService service;
    @Autowired
    private PositionService positionService;
    @GetMapping("/users")
    public ResponseEntity<Collection<GetUserDto>> getUsers(){
        return new ResponseEntity<>(service.getAllDto(), HttpStatus.OK);
    }

    @PostMapping("/createPosition")
    public ResponseEntity<Position> createPosition(@RequestBody Position position){
        positionService.createPosition(position);
        return new ResponseEntity<>(position,HttpStatus.OK);
    }

    @GetMapping("/positionsApplies")
    public ResponseEntity<GetPositionAppliesDto> getPositionApplies(@RequestParam Long id){
        try {
            GetPositionAppliesDto position = positionService.findById(id);
            return new ResponseEntity<>(position,HttpStatus.OK);

        }catch (NotFoundPositionException e){
            return new ResponseEntity<>(new GetPositionAppliesDto(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/positions")
    public ResponseEntity<List<GetPositionAppliesDto>> getPositions(){
        return new ResponseEntity<>(positionService.getAll(), HttpStatus.OK);
    }

}
