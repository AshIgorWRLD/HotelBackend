package ru.nsu.ashikhmin.hotel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ashikhmin.hotel_app.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByLoginAndPassword(String login, String password);
}
