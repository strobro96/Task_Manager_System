package ru.em.taskmanagersystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.em.taskmanagersystem.model.Comment;


@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
