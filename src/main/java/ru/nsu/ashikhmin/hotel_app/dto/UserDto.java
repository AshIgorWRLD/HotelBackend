package ru.nsu.ashikhmin.hotel_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long organisationId;

    public String toString() {
        return "\nUser{" + "login=" + this.login + ", password=" + this.password +
                ", email=" + this.email + ", firstName=" + this.firstName + ", lastName=" +
                this.lastName + ", createdAt=" + this.createdAt + ", updatedAt=" +
                this.updatedAt + ", organisation_id=" + this.organisationId + ", role=" + this.role + "}";
    }
}
