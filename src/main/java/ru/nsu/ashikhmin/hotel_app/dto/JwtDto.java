package ru.nsu.ashikhmin.hotel_app.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {

    private Long userId;
    private String jwtToken;

    @Override
    public String toString() {
        return "\nJwt{" + "user_id=" + this.userId + ", jwt_token=" +
                this.jwtToken + "}";
    }
}
