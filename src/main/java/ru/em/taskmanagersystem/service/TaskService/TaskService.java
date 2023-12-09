package ru.em.taskmanagersystem.service.TaskService;


import ru.em.taskmanagersystem.model.Comment;
import ru.em.taskmanagersystem.model.Status;
import ru.em.taskmanagersystem.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    void saveTask(Task task);

    void updateTask(Task task);

    void deleteTask(Long id);

    Task getTaskById(Long id);

    Task findTaskByIdWithComments(Long id);

    Comment addCommentToTask(Long taskId, Comment comment);

    List<Task> findTasksByAuthorId(Long authorId);

    List<Task> findTasksByEmployeeId(Long employeeId);

    void updateTaskStatus(Long taskId, Status status);

}
