package ru.nsu.ashikhmin.hotel_app.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "jwt")
@Getter
@Setter
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @NotBlank
    @Column(name = "jwt_token")
    private String jwtToken;

    public Jwt() {
    }

    public Jwt(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

    @Override
    public String toString() {
        return "\nJwt{" + "id=" + this.id + ", user=" + this.user + ", jwt_token=" +
                this.jwtToken + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Jwt jwt = (Jwt) o;
        return id != null && Objects.equals(id, jwt.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
