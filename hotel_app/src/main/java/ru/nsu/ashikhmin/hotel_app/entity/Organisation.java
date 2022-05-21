package ru.nsu.ashikhmin.hotel_app.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "organisations")
@Getter
@Setter
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer priority;

    @NotNull
    @NotBlank
    private String name;

    public Organisation() {
    }

    public Organisation(Integer priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nOrganisation{" + "id=" + this.id + ", priority=" + this.priority + ", name=" +
                this.name + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Organisation organisation = (Organisation) o;
        return id != null && Objects.equals(id, organisation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
