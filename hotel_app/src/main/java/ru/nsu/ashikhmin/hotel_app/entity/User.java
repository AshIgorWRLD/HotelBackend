package ru.nsu.ashikhmin.hotel_app.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 5)
    @Column(unique = true)
    private String login;

    @NotBlank
    @NotNull
    @Size(min = 3)
    private String password;

    @NotBlank
    @NotNull
    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    @NotNull
    //@JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @NotNull
    //@JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @OneToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @NotNull
   // @JsonProperty("created_at")
    @Column(name = "created_at")
    private Timestamp createdAt;

    @NotNull
   // @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @NotNull
    @OneToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "organisation_id", referencedColumnName = "id")
    private Organisation organisation;

    public User(){}

    public User(String login, String password, String email, String firstName, String lastName,
                Role role, Timestamp createdAt, Timestamp updatedAt, Organisation organisation) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.organisation = organisation;
    }

    public User(String login, String password, String email, String firstName, String lastName,
                Role role, Organisation organisation) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.organisation = organisation;
    }


    @Override
    public String toString(){
        return "\nUser{" + "id=" + this.id + ", login=" + this.login + ", password=" + this.password +
                ", email=" + this.email + ", firstName=" + this.firstName + ", lastName=" +
                this.lastName + ", createdAt=" + this.createdAt + ", updatedAt=" +
                this.updatedAt + ", organisation=" + this.organisation + ", role=" + this.role + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)){
            return false;
        }
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

