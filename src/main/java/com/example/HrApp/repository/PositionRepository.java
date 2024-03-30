package com.example.HrApp.repository;

import com.example.HrApp.entity.Position;
import com.example.HrApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByPositionName(String positionName);

    List<Position> findByPositionContaining(String keyword);
}
