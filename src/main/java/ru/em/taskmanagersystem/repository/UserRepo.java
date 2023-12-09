package ru.em.taskmanagersystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.em.taskmanagersystem.model.User;


import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("Select u from User u left join fetch u.roles where u.email=:email")
    Optional<User> findByEmail(String email);
}
