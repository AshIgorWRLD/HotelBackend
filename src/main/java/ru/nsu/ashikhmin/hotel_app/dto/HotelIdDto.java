package ru.nsu.ashikhmin.hotel_app.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelIdDto {
    private Long hotelId;

    @Override
    public String toString(){
        return "HotelIdDto{hotelId=" + this.hotelId + "}";
    }
}
