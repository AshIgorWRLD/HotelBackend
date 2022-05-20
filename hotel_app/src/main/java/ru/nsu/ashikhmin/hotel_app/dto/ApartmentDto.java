package ru.nsu.ashikhmin.hotel_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    //@JsonProperty("rooms_total")
    private Integer roomsTotal;
    //@JsonProperty("price_per_day")
    private Integer pricePerDay;
    //@JsonProperty("hotel_id")
    private Long hotelId;
    private String name;
    private String description;
    //@JsonProperty("available_count")
    private Integer availableCount;
}
