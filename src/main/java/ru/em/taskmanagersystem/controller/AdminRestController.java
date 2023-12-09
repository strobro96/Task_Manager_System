package ru.em.taskmanagersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.em.taskmanagersystem.model.Role;
import ru.em.taskmanagersystem.model.User;
import ru.em.taskmanagersystem.service.RoleService.RoleService;
import ru.em.taskmanagersystem.service.UserService.UserService;


import java.util.List;

@Tag(name = "Контроллер для администратора", description = "Позволяет создавать, получать, изменять, удалять пользователей")
@RestController
@RequestMapping("api/users/")
public class AdminRestController {

    private final UserService userService;

    private final RoleService roleService;


    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }

    @Operation(
            summary = "Получает информацию о всех пользователях из БД",
            description = "Выводит информацию во вкладке \"Панель управления\""
    )
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(
            summary = "Получает информацию о всех ролях из БД",
            description = "Используется в коде JavaScript"
    )
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Operation(
            summary = "Получает одного пользователя по его ID",
            description = "Используется в коде JavaScript (модальные окна \"Добавить пользователя/Редактировать/Удалить\")"
    )
    @GetMapping("{id}")
    public ResponseEntity<User> getOneUser(@PathVariable("id") @Parameter(description = "ID пользователя") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
            summary = "Сохраняет нового пользователя",
            description = "Используется в коде JavaScript (модальное окно \"Добавить пользователя\")"
    )
    @PostMapping()
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
            summary = "Изменяет пользователя",
            description = "Используется в коде JavaScript (модальное окно \"Редактировать\")"
    )
    @PatchMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
            summary = "Удаляет пользователя",
            description = "Используется в коде JavaScript (модальное окно \"Удалить\")"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") @Parameter(description = "ID удаляемого пользователя") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
