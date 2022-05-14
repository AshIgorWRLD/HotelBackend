package ru.nsu.ashikhmin.hotel_app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

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
    @JsonProperty("cognito_id")
    @Column(name = "cognito_id", unique = true)
    private String cognitoId;

    @NotBlank
    @NotNull
    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    @NotNull
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @NotNull
    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @NotNull
    private String role;

    @NotNull
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Timestamp createdAt;

    @NotNull
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @NotNull
    @OneToOne
    private Organisation organisation;

    public User(){}

    public User(String cognitoId, String email, String firstName, String lastName, String role,
                Timestamp createdAt, Timestamp updatedAt, Organisation organisation) {
        this.cognitoId = cognitoId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.organisation = organisation;
    }

    public User(String cognitoId, String email, String firstName, String lastName, String role,
                 Organisation organisation) {
        this.cognitoId = cognitoId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.organisation = organisation;
    }

    @Override
    public String toString(){
        return "\nUser{" + "id=" + this.id + ", cognitoId=" + this.cognitoId + ", email=" +
                this.email + ", firstName=" + this.firstName + ", lastName=" + this.lastName +
                ", role=" + this.role + ", createdAt=" + this.createdAt +
                ", updatedAt=" + this.updatedAt + ", organisation=" + this.organisation + "}";
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

