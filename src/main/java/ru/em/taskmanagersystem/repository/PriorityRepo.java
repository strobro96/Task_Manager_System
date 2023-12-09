package ru.em.taskmanagersystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.em.taskmanagersystem.model.Priority;


@Repository
public interface PriorityRepo extends JpaRepository<Priority, Long> {
}
