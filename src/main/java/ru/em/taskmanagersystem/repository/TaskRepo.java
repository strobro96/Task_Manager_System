package ru.em.taskmanagersystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.em.taskmanagersystem.model.Task;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.commentList WHERE t.id = :id")
    Optional<Task> findTaskByIdWithComments(@Param("id") Long id);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.commentList WHERE t.author.id = :authorId")
    List<Task> findTasksByAuthorId(Long authorId);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.commentList WHERE t.employee.id = :employeeId")
    List<Task> findTasksByEmployeeId(Long employeeId);

}
