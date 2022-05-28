package ru.nsu.ashikhmin.hotel_app.dto;

import lombok.*;
import ru.nsu.ashikhmin.hotel_app.utils.SQLAdds;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomApartmentDto {

    private Integer lowestFloor;
    private Integer highestFloor;
    private Integer lowestRoomsTotal;
    private Integer highestRoomsTotal;
    private Integer lowestPrice;
    private Integer highestPrice;
    private List<HotelIdDto> hotels;
    private String searchQuery;
    private Integer lowestAvailableCount;
    private Integer highestAvailableCount;

    @Override
    public String toString() {
        return "CustomApartmentDto{" + "lowestFloor=" + this.lowestFloor + ", highestFloor=" +
                this.highestFloor + ", lowestRoomsTotal=" + this.lowestRoomsTotal +
                ", highestRoomsTotal=" + this.highestRoomsTotal + ", lowestPrice" +
                this.lowestPrice + ", highestPrice=" + this.highestPrice +
                ", hotels=" + this.hotels + ", searchQuery=" + this.searchQuery + ", lowestAvailableCount=" +
                this.lowestAvailableCount + ", highestAvailableCount=" + this.highestAvailableCount;
    }

    public boolean isNotEmpty() {
        return lowestFloor != null || highestFloor != null || lowestRoomsTotal != null ||
                highestRoomsTotal != null || lowestPrice != null || highestPrice != null ||
                hotels != null || searchQuery != null || lowestAvailableCount != null ||
                highestAvailableCount != null;
    }

    public boolean isSearchQuery() {
        return searchQuery != null;
    }

    public Integer countHotelAmount() {
        return hotels.size();
    }

    public boolean isHotel() {
        return hotels != null;
    }

    public boolean isPriceAnd() {
        return lowestPrice != null && highestPrice != null;
    }

    public boolean isPrice() {
        return lowestPrice != null || highestPrice != null;
    }

    public boolean isFloorAnd() {
        return lowestFloor != null && highestFloor != null;
    }

    public boolean isFloor() {
        return lowestFloor != null || highestFloor != null;
    }

    public boolean isRoomsTotalAnd() {
        return lowestRoomsTotal != null && highestRoomsTotal != null;
    }

    public boolean isRoomsTotal() {
        return lowestRoomsTotal != null || highestRoomsTotal != null;
    }

    public boolean isAvailableCountAnd() {
        return lowestAvailableCount != null && highestAvailableCount != null;
    }

    public boolean isAvailableCount() {
        return lowestAvailableCount != null || highestAvailableCount != null;
    }

    public void addHotels(StringBuilder stringBuilder, Boolean[] isNotFirst) {
        if (!isHotel()) {
            return;
        }
        if (isNotFirst[0]) {
            stringBuilder.append(" ")
                    .append(SQLAdds.AND);
        } else {
            isNotFirst[0] = true;
        }
        Integer hotelAmount = countHotelAmount();
        stringBuilder.append(" (");
        for (int i = 0; i < hotelAmount; i++) {
            if (i > 0) {
                stringBuilder.append(" ")
                        .append(SQLAdds.OR)
                        .append(" ");
            }
            stringBuilder.append(SQLAdds.APARTMENT_TABLE)
                    .append(".hotel_id = ")
                    .append(hotels.get(i).getHotelId());
        }
        stringBuilder.append(")");
    }

    public void addSearchQuery(StringBuilder stringBuilder, Boolean[] isNotFirst) {
        if (!isSearchQuery()) {
            return;
        }
        if (isNotFirst[0]) {
            stringBuilder.append(" ")
                    .append(SQLAdds.AND);
        } else {
            isNotFirst[0] = true;
        }
        stringBuilder.append(" ")
                .append(SQLAdds.APARTMENT_TABLE)
                .append(".name ")
                .append(SQLAdds.ILIKE)
                .append(" '%")
                .append(searchQuery)
                .append("%'");
    }

    public void addBarParameter(StringBuilder stringBuilder, Boolean[] isNotFirst,
                                boolean isNotEmpty, boolean isAnd, Integer lowestValue,
                                Integer highestValue, String columnName) {
        if (!isNotEmpty) {
            return;
        }
        if (isNotFirst[0]) {
            stringBuilder.append(" ")
                    .append(SQLAdds.AND);
        } else {
            isNotFirst[0] = true;
        }
        stringBuilder.append(" ")
                .append(SQLAdds.APARTMENT_TABLE)
                .append(columnName);
        if (isAnd) {
            stringBuilder.append(" ")
                    .append(SQLAdds.BETWEEN)
                    .append(" ")
                    .append(lowestValue)
                    .append(" ")
                    .append(SQLAdds.AND)
                    .append(" ")
                    .append(highestValue);
        } else {
            if (lowestValue != null) {
                stringBuilder.append(" >= ")
                        .append(lowestValue);
            } else {
                stringBuilder.append(" <= ")
                        .append(highestValue);
            }
        }
    }
}

