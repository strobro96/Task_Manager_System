package ru.em.taskmanagersystem.service.UserService;


import ru.em.taskmanagersystem.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);


}
