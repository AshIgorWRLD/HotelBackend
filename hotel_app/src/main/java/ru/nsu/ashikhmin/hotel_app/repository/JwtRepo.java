package ru.nsu.ashikhmin.hotel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ashikhmin.hotel_app.entity.Jwt;

public interface JwtRepo extends JpaRepository<Jwt, Long> {

    Jwt findByUserId(Long id);
}
