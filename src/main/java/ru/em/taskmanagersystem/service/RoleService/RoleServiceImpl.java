package ru.em.taskmanagersystem.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.taskmanagersystem.model.Role;
import ru.em.taskmanagersystem.repository.RoleRepo;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
