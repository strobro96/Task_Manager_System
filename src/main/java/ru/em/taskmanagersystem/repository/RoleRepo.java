package ru.em.taskmanagersystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.em.taskmanagersystem.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
