package com.example.HrApp.service.abstraction;

import com.example.HrApp.entity.Role;
import com.example.HrApp.entity.User;

import java.util.List;

public interface IRoleService {
    Role createRole(Role role);
    List<Role> getAll();
    Role findById(Long id);
    Role findByUsername(String Name);
    void delete(Long id);
}
