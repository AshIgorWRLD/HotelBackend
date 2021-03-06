package ru.nsu.ashikhmin.hotel_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "apartments")
@Getter
@Setter
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer floor;

    @NotNull
    @Column(name = "rooms_total")
    private Integer roomsTotal;

    @NotNull
    @Column(name = "price_per_day")
    private Integer pricePerDay;

    @JsonManagedReference
    @NotNull
    @ManyToOne()
    private Hotel hotel;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Column(name = "available_count")
    private Integer availableCount;

    public Apartment() {
    }

    public Apartment(Integer floor, Integer roomsTotal, Integer pricePerDay, Hotel hotel,
                     String name, String description, Integer availableCount) {
        this.floor = floor;
        this.roomsTotal = roomsTotal;
        this.pricePerDay = pricePerDay;
        this.hotel = hotel;
        this.name = name;
        this.description = description;
        this.availableCount = availableCount;
    }

    @Override
    public String toString() {
        return "\nApartment{" + "id=" + this.id + ", floor=" + this.floor + ", rooms_total=" +
                this.roomsTotal + ", price_per_day=" + this.pricePerDay + ", hotel=" +
                this.hotel + ", name=" + this.name + ", description=" + this.description +
                ", available_count=" + this.availableCount + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Apartment apartment = (Apartment) o;
        return id != null && Objects.equals(id, apartment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
