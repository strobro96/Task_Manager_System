package ru.em.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.em.taskmanagersystem.common.StatusEnum;
import ru.em.taskmanagersystem.model.Comment;
import ru.em.taskmanagersystem.model.Task;
import ru.em.taskmanagersystem.service.TaskService.TaskService;

import java.util.List;


@Tag(name = "Контроллер для задач", description = "Позволяет создавать, получать, изменять, удалять задачи и др.")
@RestController
@RequestMapping("api/tasks/")
public class TaskRestController {

    private final TaskService taskService;

    @Autowired
    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Получает информацию о всех задачах из БД",
            description = "Выводит информацию во вкладке \"Таскборд\""
    )
    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(
            summary = "Получает информацию о всех задачах конкретного автора и комментариях к ним",
            description = "Функционал пока не реализован для фронтенда приложения"
    )
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Task>> getAllTasksOfAuthor(@PathVariable("authorId") @Parameter(description = "ID автора задач") Long id) {
        List<Task> tasks = taskService.findTasksByAuthorId(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(
            summary = "Получает информацию о всех задачах конкретного исполнителя и комментариях к ним",
            description = "Функционал пока не реализован для фронтенда приложения"
    )
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Task>> getAllTasksOfEmployee(@PathVariable("employeeId") @Parameter(description = "ID исполнителя задач") Long id) {
        List<Task> tasks = taskService.findTasksByEmployeeId(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(
            summary = "Получает задачу и все комментарии к ней по ID",
            description = "Используется в коде JavaScript при переходе в \"Окно задачи\")"
    )
    @GetMapping("{id}")
    public ResponseEntity<Task> getOneTask(@PathVariable("id") @Parameter(description = "ID задачи") Long id) {
        Task task = taskService.findTaskByIdWithComments(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Operation(
            summary = "Сохраняет новую задачу",
            description = "Используется в коде JavaScript (модальное окно \"Добавить задачу\")"
    )
    @PostMapping()
    public ResponseEntity<Task> addNewTask(@RequestBody Task task) {
        taskService.saveTask(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Operation(
            summary = "Добавляет к задаче комментарий",
            description = "Используется в коде JavaScript в \"Окне задачи\")"
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addCommentToTask(@PathVariable @Parameter(description = "ID задачи") Long id, @RequestBody Comment comment) {
        Comment createdComment = taskService.addCommentToTask(id, comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Изменяет задачу (только для автора задачи)",
            description = "Используется в коде JavaScript (модальное окно \"Редактировать\")"
    )
    @PatchMapping()
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @Operation(
            summary = "Удаляет задачу (только для автора задачи)",
            description = "Используется в коде JavaScript (модальное окно \"Удалить\")"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") @Parameter(description = "ID задачи") Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Изменяет статус задачи (для исполнителя)",
            description = "Функционал пока не реализован для фронтенда приложения"
    )
    @PatchMapping("/updateTaskStatus/{id}")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable @Parameter(description = "ID задачи") Long id, @RequestBody StatusEnum statusEnum) {
        taskService.updateTaskStatus(id, statusEnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
