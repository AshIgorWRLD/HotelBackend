package ru.nsu.ashikhmin.hotel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ashikhmin.hotel_app.entity.Organisation;

import java.util.Optional;

public interface OrganisationRepo extends JpaRepository<Organisation, Long> {

    Optional<Organisation> findByName(String name);
}
