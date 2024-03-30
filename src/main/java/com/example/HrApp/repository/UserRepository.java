package com.example.HrApp.repository;

import com.example.HrApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    boolean existsByUserName(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.role r")
    List<User> findAllUsersWithRole();

    List<User> findByUsernameContaining(String keyword);
}
