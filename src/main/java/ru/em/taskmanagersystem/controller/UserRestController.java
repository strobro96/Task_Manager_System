package ru.em.taskmanagersystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.em.taskmanagersystem.model.User;


@Tag(name = "Контроллер для пользователя", description = "Получение информации для вкладки \"Профиль\"")
@RestController
@RequestMapping("api/user/")
public class UserRestController {

    @Operation(
            summary = "Получает информацию о текущем пользователе из Spring Security Context",
            description = "Выводит информацию во вкладке \"Профиль\""
    )
    @GetMapping()
    public ResponseEntity<User> getUserHomePage() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}