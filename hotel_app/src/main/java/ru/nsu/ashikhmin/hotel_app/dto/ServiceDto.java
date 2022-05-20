package ru.nsu.ashikhmin.hotel_app.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    private String name;
    private boolean isAdditional;
    private Integer totalPrice;
    private String description;
    private Long hotelId;

    @Override
    public String toString(){
        return "\nService{" + "name=" + this.name + ", is_additional=" +
                this.isAdditional + ", total_price=" + this.totalPrice + ", description=" +
                this.description + ", hotel_id=" + this.hotelId + "}";
    }
}
