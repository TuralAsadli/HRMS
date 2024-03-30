package com.example.HrApp.service;

import com.example.HrApp.entity.Role;
import com.example.HrApp.repository.RoleRepository;
import com.example.HrApp.service.abstraction.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class RoleService implements IRoleService {

    @Autowired
    RoleRepository repository;

    @Override
    public Role createRole(Role role) {

        Role createdRole = repository.save(role);
        log.info("IN register - role : {} successfully registered.", createdRole);

        return createdRole;
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Role findByUsername(String Name) {
        return repository.findByRoleName(Name);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        log.info("IN delete - role: {} found by id: {}", id);
    }
}
