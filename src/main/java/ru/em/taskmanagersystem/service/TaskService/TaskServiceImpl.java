package ru.em.taskmanagersystem.service.TaskService;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.taskmanagersystem.model.Comment;
import ru.em.taskmanagersystem.model.Status;
import ru.em.taskmanagersystem.model.Task;
import ru.em.taskmanagersystem.model.User;
import ru.em.taskmanagersystem.repository.CommentRepo;
import ru.em.taskmanagersystem.repository.TaskRepo;


import javax.persistence.EntityNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Log4j2
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    private final CommentRepo commentRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo, CommentRepo commentRepo) {
        this.taskRepo = taskRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTask(Task task) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setAuthor(user);
        taskRepo.save(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTask(Task task) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long authorId = taskRepo.getById(task.getId()).getAuthor().getId();
        if (user.getId() == authorId) {
            task.setAuthor(user);
            taskRepo.save(task);
        } else {
            log.log(Level.WARN, "Вы не являетесь автором задачи!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId() == taskRepo.getById(id).getAuthor().getId()) {
            taskRepo.deleteById(id);
        } else {
            log.log(Level.WARN, "Вы не являетесь автором задачи!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepo.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Task findTaskByIdWithComments(Long id) {
        return taskRepo.findTaskByIdWithComments(id).get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Comment addCommentToTask(Long taskId, Comment comment) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена задача с id " + taskId));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setAuthor(user);
        comment.setZonedDateTime(ZonedDateTime.now(ZoneId.of("Europe/Moscow")));
        commentRepo.save(comment);
        task.getCommentList().add(comment);
        taskRepo.save(task);
        return comment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Task> findTasksByAuthorId(Long authorId) {
        return taskRepo.findTasksByAuthorId(authorId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Task> findTasksByEmployeeId(Long employeeId) {
        return taskRepo.findTasksByEmployeeId(employeeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskStatus(Long taskId, Status status) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена задача с id " + taskId));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId() == (task.getEmployee().getId())) {
            task.setStatusEnum(status);
            taskRepo.save(task);
        } else {
            log.log(Level.WARN, "Вы не являетесь исполнителем задачи!");
        }
    }
}

