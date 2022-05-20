package ru.nsu.ashikhmin.hotel_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    //@JsonProperty("first_name")
    private String firstName;
    //@JsonProperty("last_name")
    private String lastName;
    private String role;
    //@JsonProperty("created_at")
    private Timestamp createdAt;
    //@JsonProperty("updated_at")
    private Timestamp updatedAt;
    //@JsonProperty("organisation_id")
    private Long organisationId;

    public String toString(){
        return "\nUser{" + "login=" + this.login + ", password=" + this.password +
                ", email=" + this.email + ", firstName=" + this.firstName + ", lastName=" +
                this.lastName + ", createdAt=" + this.createdAt + ", updatedAt=" +
                this.updatedAt + ", organisation_id=" + this.organisationId + ", role=" + this.role + "}";
    }
}
