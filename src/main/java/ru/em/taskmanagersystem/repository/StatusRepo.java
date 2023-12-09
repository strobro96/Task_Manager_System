package ru.em.taskmanagersystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.em.taskmanagersystem.model.Status;


@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {
}
