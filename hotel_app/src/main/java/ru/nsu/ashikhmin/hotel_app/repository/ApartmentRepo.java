package ru.nsu.ashikhmin.hotel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.ashikhmin.hotel_app.entity.Apartment;

public interface ApartmentRepo extends JpaRepository<Apartment, Long> {

}
