package ru.em.taskmanagersystem.service.CommentService;


import ru.em.taskmanagersystem.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    void saveComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Long id);

    Comment getCommentById(Long id);

}
