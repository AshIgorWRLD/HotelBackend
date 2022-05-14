package ru.nsu.ashikhmin.hotel_app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ashikhmin.hotel_app.entity.Hotel;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.HotelRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("hotels")
@Api(description = "Контроллер отелей")
public class HotelController {

    private final HotelRepo hotelRepo;

    @Autowired
    public HotelController(HotelRepo hotelRepo){
        this.hotelRepo = hotelRepo;
    }

    @GetMapping
    @ApiOperation("Получение списка отелей")
    public ResponseEntity<List<Hotel>> list(){
        log.info("request for getting all hotels");
        List<Hotel> hotels = hotelRepo.findAll();
        if(hotels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Получение отеля по id")
    public ResponseEntity<Hotel> getOne(@PathVariable("id") Long id) {
        log.info("request for getting hotel with id: {}", id);

        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found hotel with id = " + id));

        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание нового отеля")
    public ResponseEntity<Hotel> create(@Valid @RequestBody Hotel hotel){
        log.info("request for creating hotel with parameters {}", hotel);

        return new ResponseEntity<>(hotelRepo.save(hotel), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующем отеле")
    public ResponseEntity<Hotel> update(@PathVariable("id") long id,
                                       @Valid @RequestBody Hotel hotel){
        log.info("request for updating hotel by id {} with parameters {}",
                id, hotel);

        Hotel hotelFromDataBase = hotelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found hotel with id = " + id));
        log.info("Hotel from data base: " + hotelFromDataBase);

        BeanUtils.copyProperties(hotel, hotelFromDataBase,
                NullProperty.getNullPropertiesString(hotel));
        return new ResponseEntity<>(hotelRepo.save(hotelFromDataBase), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление отеля по id")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Hotel hotel) {

        log.info("request for deleting hotel with parameters {}", hotel);

        hotelRepo.delete(hotel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("Удаление всех отелей")
    public ResponseEntity<HttpStatus> deleteAll(){
        log.info("request for deleting all hotels");
        hotelRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
