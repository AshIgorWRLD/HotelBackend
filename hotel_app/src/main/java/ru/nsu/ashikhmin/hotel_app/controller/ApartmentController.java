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
import ru.nsu.ashikhmin.hotel_app.dto.ApartmentDto;
import ru.nsu.ashikhmin.hotel_app.entity.Apartment;
import ru.nsu.ashikhmin.hotel_app.entity.Hotel;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.ApartmentRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@Validated
@RestController
@RequestMapping("hotels/apartments")
@Api(description = "Контроллер апартаментов")
public class ApartmentController {

    private final ApartmentRepo apartmentRepo;

    private final HotelController hotelController;

    @Autowired
    public ApartmentController(ApartmentRepo apartmentRepo, HotelController hotelController){
        this.apartmentRepo = apartmentRepo;
        this.hotelController = hotelController;
    }

    @GetMapping
    @ApiOperation("Получение списка апартаментов")
    public ResponseEntity<List<Apartment>> list(){
        log.info("request for getting all apartments");
        List<Apartment> apartments = apartmentRepo.findAll();
        if(apartments.isEmpty()){
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

    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание новых апартаментов")
    public ResponseEntity<Apartment> create(@Valid @RequestBody ApartmentDto apartmentDto){
        log.info("request for creating apartment from data source {}", apartmentDto);
        ResponseEntity<Hotel> hotelResponseEntity = hotelController.getOne(
                apartmentDto.getHotelId());
        Hotel hotel = hotelResponseEntity.getBody();
        Apartment apartment = new Apartment(apartmentDto.getFloor(), apartmentDto.getRoomsTotal(),
                apartmentDto.getPricePerDay(), hotel, apartmentDto.getName(),
                apartmentDto.getDescription(), apartmentDto.getAvailableCount());

        log.info("request for creating apartment with parameters {}", apartment);

        return new ResponseEntity<>(apartmentRepo.save(apartment), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующих апартаментах")
    public ResponseEntity<Apartment> update(@PathVariable("id") long id,
                                       @Valid @RequestBody ApartmentDto apartmentDto){
        log.info("request for updating apartment from data source {}", apartmentDto);
        ResponseEntity<Hotel> hotelResponseEntity = hotelController.getOne(
                apartmentDto.getHotelId());
        Hotel body = hotelResponseEntity.getBody();
        Apartment apartment = new Apartment(apartmentDto.getFloor(), apartmentDto.getRoomsTotal(),
                apartmentDto.getPricePerDay(), body,apartmentDto.getName(),
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
    public ResponseEntity<HttpStatus> deleteAll(){
        log.info("request for deleting all apartments");
        apartmentRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
