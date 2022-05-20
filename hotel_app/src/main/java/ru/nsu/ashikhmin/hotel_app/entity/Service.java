package ru.nsu.ashikhmin.hotel_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "services")
@Getter
@Setter
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String name;

    @NotNull
    @Column(name = "is_additional")
    private boolean isAdditional;

    @NotNull
    @Column(name = "total_price")
    private Integer totalPrice;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @JsonBackReference
    @ManyToMany (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinTable(name = "services_hotels",
            joinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "hotel_id", referencedColumnName = "id")})
    private List<Hotel> hotels = new ArrayList<>();

    public Service(){}

    public Service(String name, boolean isAdditional, Integer totalPrice, String description,
                   List<Hotel> hotels) {
        this.name = name;
        this.isAdditional = isAdditional;
        this.totalPrice = totalPrice;
        this.description = description;
        this.hotels = hotels;
    }

    @Override
    public String toString(){
        return "\nService{" + "id=" + this.id + ", name=" + this.name + ", is_additional=" +
                this.isAdditional + ", total_price=" + this.totalPrice + ", description=" +
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
        Service service = (Service) o;
        return id != null && Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
