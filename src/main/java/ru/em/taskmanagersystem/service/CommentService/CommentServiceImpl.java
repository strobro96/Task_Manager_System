package ru.em.taskmanagersystem.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.taskmanagersystem.model.Comment;
import ru.em.taskmanagersystem.repository.CommentRepo;


import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(Comment comment) {
        commentRepo.save(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateComment(Comment comment) {
        commentRepo.save(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id) {
        commentRepo.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Comment getCommentById(Long id) {
        return commentRepo.getById(id);
    }
}
