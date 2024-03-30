package com.example.HrApp.service;

import com.example.HrApp.dto.positions.GetPositionAppliesDto;
import com.example.HrApp.entity.Position;
import com.example.HrApp.entity.User;
import com.example.HrApp.exception.UserNotFoundExceptionById;
import com.example.HrApp.exception.position.NotFoundPositionException;
import com.example.HrApp.repository.PositionRepository;
import com.example.HrApp.repository.UserRepository;
import com.example.HrApp.service.abstraction.IPositionService;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionService implements IPositionService {

    @Autowired
    private PositionRepository repository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Position createPosition(Position position) {
        repository.save(position);
        return position;
    }

    @Override
    public List<GetPositionAppliesDto> getAll() {
        List<Position> positions = repository.findAll();
        List<GetPositionAppliesDto> dtos = new ArrayList<>();
        for (Position position : positions){

            GetPositionAppliesDto dto = new GetPositionAppliesDto();
            dto.setId(position.getId());
            dto.setPositionName(position.getPositionName());
            dto.setCreated(position.getCreated());
            dto.setUpdated(position.getUpdated());
            dto.setSalary(position.getSalary());
            dto.setRequirements(position.getRequirements());
            dto.setWorkingDays(position.getWorkingDays());
            dto.setWorkingHours(position.getWorkingHours());
            dto.setInHoliday(position.isInHoliday());
            dto.setUsers(position.getUsers());

            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public GetPositionAppliesDto findById(Long id) throws NotFoundPositionException {
        Position position = repository.findById(id).orElse(null);
        if (position == null){
            throw new NotFoundPositionException("Can't find position with id:" + id);
        }
        GetPositionAppliesDto dto = new GetPositionAppliesDto();
        dto.setId(position.getId());
        dto.setPositionName(position.getPositionName());
        dto.setCreated(position.getCreated());
        dto.setUpdated(position.getUpdated());
        dto.setSalary(position.getSalary());
        dto.setRequirements(position.getRequirements());
        dto.setWorkingDays(position.getWorkingDays());
        dto.setWorkingHours(position.getWorkingHours());
        dto.setInHoliday(position.isInHoliday());
        dto.setUsers(position.getUsers());

        return dto;
    }

    @Override
    public Position findByUsername(String Name) throws NotFoundPositionException {
        Position position = repository.findByPositionName(Name);
        if (position == null){
            throw new NotFoundPositionException("Can't find position with name:" + Name);
        }
        return position;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void addApply(Long userId, Long positionId) throws UserNotFoundExceptionById, NotFoundPositionException {
        User user = userRepository.findById(userId).orElse(null);
        Position position = repository.findById(positionId).orElse(null);
        if (user == null){
            throw new UserNotFoundExceptionById("Can't find user with id:" + userId);
        } else if (position == null) {
            throw new NotFoundPositionException("Not Found Position with id:"+positionId);
        }
        List<Position> userPositions = user.getPositions();
        List<User> positionsUsers = position.getUsers();

        userPositions.add(position);
        positionsUsers.add(user);

        user.setPositions(userPositions);
        position.setUsers(positionsUsers);

        userRepository.save(user);
        repository.save(position);


    }
}
