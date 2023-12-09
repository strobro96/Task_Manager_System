package ru.em.taskmanagersystem.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.taskmanagersystem.model.User;
import ru.em.taskmanagersystem.repository.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        userRepo.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public User getUserById(Long id) {
        User user = null;
        Optional<User> userWithId = userRepo.findById(id);
        if (userWithId.isPresent()) {
            user = userWithId.get();
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

}
