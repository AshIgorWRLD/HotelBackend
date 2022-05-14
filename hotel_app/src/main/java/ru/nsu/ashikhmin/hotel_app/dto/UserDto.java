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

    @JsonProperty("cognito_id")
    private String cognitoId;
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String role;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @JsonProperty("updated_at")
    private Timestamp updatedAt;
    @JsonProperty("organisation_id")
    private Long organisationId;
}
