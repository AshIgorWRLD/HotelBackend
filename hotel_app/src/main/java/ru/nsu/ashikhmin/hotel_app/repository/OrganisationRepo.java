package ru.nsu.ashikhmin.hotel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ashikhmin.hotel_app.entity.Organisation;

public interface OrganisationRepo extends JpaRepository<Organisation, Long> {

    Organisation findByName(String name);
}
