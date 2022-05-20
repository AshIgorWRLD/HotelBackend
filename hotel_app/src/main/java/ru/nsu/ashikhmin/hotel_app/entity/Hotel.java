package ru.nsu.ashikhmin.hotel_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hotels")
@Getter
@Setter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer stars;

    @NotNull
    //@JsonProperty("floorsTotal")
    @Column(name = "floorsTotal")
    private Integer floorsTotal;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    private List<Apartment> apartmentList;

    public Hotel(){}

    public Hotel(String name, Integer stars, Integer floorsTotal, String description) {
        this.name = name;
        this.stars = stars;
        this.floorsTotal = floorsTotal;
        this.description = description;
    }

    @Override
    public String toString(){
        return "\nHotel{" + "id=" + this.id + ", name=" + this.name + ", stars=" +
                this.stars + ", floorsTotal=" + this.floorsTotal + ", description=" +
                this.description + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)){
            return false;
        }
        Hotel hotel = (Hotel) o;
        return id != null && Objects.equals(id, hotel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
