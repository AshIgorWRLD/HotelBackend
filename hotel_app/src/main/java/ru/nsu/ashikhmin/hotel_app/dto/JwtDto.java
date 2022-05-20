package ru.nsu.ashikhmin.hotel_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("jwt_token")
    private String jwtToken;

    @Override
    public String toString(){
        return "\nJwt{" + "user_id=" + this.userId + ", jwt_token=" +
                this.jwtToken + "}";
    }
}
