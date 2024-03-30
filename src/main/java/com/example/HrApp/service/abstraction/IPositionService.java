package com.example.HrApp.service.abstraction;

import com.example.HrApp.dto.positions.GetPositionAppliesDto;
import com.example.HrApp.entity.Position;
import com.example.HrApp.entity.Role;
import com.example.HrApp.entity.User;
import com.example.HrApp.exception.UserNotFoundExceptionById;
import com.example.HrApp.exception.position.NotFoundPositionException;
import org.springframework.beans.NotReadablePropertyException;

import java.util.List;

public interface IPositionService {
    Position createPosition(Position position);
    List<GetPositionAppliesDto> getAll();
    GetPositionAppliesDto findById(Long id) throws NotReadablePropertyException, NotFoundPositionException;
    Position findByUsername(String Name) throws NotFoundPositionException;
    void delete(Long id);

    void addApply(Long userId, Long positionId) throws UserNotFoundExceptionById, NotFoundPositionException;
}
