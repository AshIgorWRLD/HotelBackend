package ru.nsu.ashikhmin.hotel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ashikhmin.hotel_app.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
