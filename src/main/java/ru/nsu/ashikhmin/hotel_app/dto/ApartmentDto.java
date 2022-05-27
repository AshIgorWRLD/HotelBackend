package ru.nsu.ashikhmin.hotel_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ApartmentDto {
    private Integer floor;
    private Integer roomsTotal;
    private Integer pricePerDay;
    private Long hotelId;
    private String name;
    private String description;
    private Integer availableCount;
}
