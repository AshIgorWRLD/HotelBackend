package ru.nsu.ashikhmin.hotel_app.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVerificationDto {
    private String login;
    private String password;

    @Override
    public String toString(){
        return "UserVerificationDto{" + "login=" + this.login + ", password=" + this.password;
    }
}
