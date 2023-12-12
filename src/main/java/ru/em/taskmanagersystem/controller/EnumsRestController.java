package ru.em.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.em.taskmanagersystem.common.PriorityEnum;
import ru.em.taskmanagersystem.common.StatusEnum;
import ru.em.taskmanagersystem.model.User;
import ru.em.taskmanagersystem.service.UserService.UserService;

import java.util.*;

@Tag(name = "Контроллер для списков", description = "Получение списков пользователей, статусов, приоритетов")
@RestController
@RequestMapping("api/enums/")
public class EnumsRestController {


    private final UserService userService;

    @Autowired
    public EnumsRestController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Получает информацию о всех приоритетах из БД",
            description = "Используется в коде JavaScript"
    )
    @GetMapping("priorities")
    public ResponseEntity<List<Map<String, Object>>> getAllPriorities() {
        List<Map<String, Object>> priorities = PriorityEnum.getAllPriorities();
        return new ResponseEntity<>(priorities, HttpStatus.OK);
    }

    @Operation(
            summary = "Получает информацию о всех статусах из БД",
            description = "Используется в коде JavaScript"
    )
    @GetMapping("statuses")
    public ResponseEntity<List<Map<String, Object>>> getAllStatuses() {
        List<Map<String, Object>> statuses = StatusEnum.getAllStatuses();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @Operation(
            summary = "Получает информацию о всех пользователях из БД",
            description = "Используется в коде JavaScript"
    )
    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
