package ru.nsu.ashikhmin.hotel_app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ashikhmin.hotel_app.dto.ApartmentDto;
import ru.nsu.ashikhmin.hotel_app.dto.CustomApartmentDto;
import ru.nsu.ashikhmin.hotel_app.entity.Apartment;
import ru.nsu.ashikhmin.hotel_app.entity.Hotel;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.ApartmentRepo;
import ru.nsu.ashikhmin.hotel_app.repository.HotelRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;
import ru.nsu.ashikhmin.hotel_app.utils.SQLAdds;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@CrossOrigin
@RestController
@EntityScan("ru.nsu.ashikhmin.hotel_app.entity")
@RequestMapping("hotels/apartments")
@Api(description = "Контроллер апартаментов")
public class ApartmentController {

    private final ApartmentRepo apartmentRepo;

    private final HotelRepo hotelRepo;
    private final EntityManager entityManager;
    @Autowired
    public ApartmentController(ApartmentRepo apartmentRepo, HotelRepo hotelRepo,
                               EntityManager entityManager) {
        this.apartmentRepo = apartmentRepo;
        this.hotelRepo = hotelRepo;
        this.entityManager = entityManager;
    }

    @GetMapping
    @ApiOperation("Получение списка апартаментов")
    public ResponseEntity<List<Apartment>> list() {
        log.info("request for getting all apartments");
        List<Apartment> apartments = apartmentRepo.findAll();
        if (apartments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @ApiOperation("Получение апартаментов по id")
    public ResponseEntity<Apartment> getOneById(@PathVariable("id") Long id) {
        log.info("request for getting apartment with id: {}", id);

        Apartment apartment = apartmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found apartment with id = " + id));

        return new ResponseEntity<>(apartment, HttpStatus.OK);
    }

    @PostMapping("/custom")
    @ApiOperation("Получение апартаментов по id")
    public ResponseEntity<List<Apartment>> getBy(
            @Valid @RequestBody CustomApartmentDto customApartmentDto) {
        log.info("request for filtering apartment with values: {}", customApartmentDto);

        StringBuilder sqlRequest = new StringBuilder();

        sqlRequest.append(SQLAdds.SELECT)
                .append(" * ")
                .append(SQLAdds.FROM)
                .append(" ")
                .append(SQLAdds.APARTMENT_TABLE)
                .append(" ");

        if(customApartmentDto.isNotEmpty()){
            Boolean[] isNotFirst = new Boolean[1];
            isNotFirst[0] = false;
            sqlRequest.append(SQLAdds.WHERE)
                    .append("(");
            customApartmentDto.addBarParameter(sqlRequest, isNotFirst, customApartmentDto.isFloor(),
                    customApartmentDto.isFloorAnd(), customApartmentDto.getLowestFloor(),
                    customApartmentDto.getHighestFloor(), ".floor");
            customApartmentDto.addBarParameter(sqlRequest, isNotFirst, customApartmentDto.isRoomsTotal(),
                    customApartmentDto.isRoomsTotalAnd(), customApartmentDto.getLowestRoomsTotal(),
                    customApartmentDto.getHighestRoomsTotal(), ".rooms_total");
            customApartmentDto.addBarParameter(sqlRequest, isNotFirst, customApartmentDto.isPrice(),
                    customApartmentDto.isPriceAnd(), customApartmentDto.getLowestPrice(),
                    customApartmentDto.getHighestPrice(), ".price_per_day");
            customApartmentDto.addBarParameter(sqlRequest, isNotFirst, customApartmentDto.isAvailableCount(),
                    customApartmentDto.isAvailableCountAnd(), customApartmentDto.getLowestAvailableCount(),
                    customApartmentDto.getHighestAvailableCount(), ".available_count");
            customApartmentDto.addHotels(sqlRequest, isNotFirst);
            customApartmentDto.addSearchQuery(sqlRequest, isNotFirst);
            sqlRequest.append(");");
        }

        List<Apartment> apartments = entityManager.createNativeQuery(sqlRequest.toString(), Apartment.class).getResultList();
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание новых апартаментов")
    public ResponseEntity<Apartment> create(@Valid @RequestBody ApartmentDto apartmentDto) {
        log.info("request for creating apartment from data source {}", apartmentDto);
        Hotel hotel = hotelRepo.findById(
                apartmentDto.getHotelId()).orElseThrow(() -> new ResourceNotFoundException(
                "Not found hotel with id = " + apartmentDto.getHotelId()));

        Apartment apartment = new Apartment(apartmentDto.getFloor(), apartmentDto.getRoomsTotal(),
                apartmentDto.getPricePerDay(), hotel, apartmentDto.getName(),
                apartmentDto.getDescription(), apartmentDto.getAvailableCount());

        log.info("request for creating apartment with parameters {}", apartment);

        return new ResponseEntity<>(apartmentRepo.save(apartment), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующих апартаментах")
    public ResponseEntity<Apartment> update(@PathVariable("id") long id,
                                            @Valid @RequestBody ApartmentDto apartmentDto) {
        log.info("request for updating apartment from data source {}", apartmentDto);
        Hotel hotel = hotelRepo.findById(
                apartmentDto.getHotelId()).orElseThrow(() -> new ResourceNotFoundException(
                "Not found hotel with id = " + apartmentDto.getHotelId()));
        Apartment apartment = new Apartment(apartmentDto.getFloor(), apartmentDto.getRoomsTotal(),
                apartmentDto.getPricePerDay(), hotel, apartmentDto.getName(),
                apartmentDto.getDescription(), apartmentDto.getAvailableCount());

        log.info("request for updating apartment by id {} with parameters {}",
                id, apartment);

        Apartment apartmentFromDataBase = apartmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found apartment with id = " + id));
        log.info("Apartment from data base: " + apartmentFromDataBase);

        BeanUtils.copyProperties(apartment, apartmentFromDataBase,
                NullProperty.getNullPropertiesString(apartment));
        return new ResponseEntity<>(apartmentRepo.save(apartmentFromDataBase), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление апартаментов по id")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Apartment apartment) {

        log.info("request for deleting apartment with parameters {}", apartment);

        apartmentRepo.delete(apartment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("Удаление всех апартаментов")
    public ResponseEntity<HttpStatus> deleteAll() {
        log.info("request for deleting all apartments");
        apartmentRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
