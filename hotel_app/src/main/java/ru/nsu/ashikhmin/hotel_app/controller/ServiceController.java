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
import ru.nsu.ashikhmin.hotel_app.dto.ServiceDto;
import ru.nsu.ashikhmin.hotel_app.entity.Hotel;
import ru.nsu.ashikhmin.hotel_app.entity.Service;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.ServiceRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("hotels/service")
@Api(description = "Контроллер сервисов")
public class ServiceController {

    private final ServiceRepo serviceRepo;

    private final HotelController hotelController;

    @Autowired
    public ServiceController(ServiceRepo serviceRepo, HotelController hotelController) {
        this.serviceRepo = serviceRepo;
        this.hotelController = hotelController;
    }

    @GetMapping
    @ApiOperation("Получение списка сервисов")
    public ResponseEntity<List<Service>> list() {
        log.info("request for getting all services");
        List<Service> services = serviceRepo.findAll();
        if (services.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @ApiOperation("Получение сервиса по id")
    public ResponseEntity<Service> getOneById(@PathVariable("id") Long id) {
        log.info("request for getting service with id: {}", id);

        Service service = serviceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found service with id = " + id));

        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание нового сервиса")
    public ResponseEntity<Service> create(@Valid @RequestBody ServiceDto serviceDto) {
        log.info("request for creating service from data source {}", serviceDto);
        ResponseEntity<Hotel> hotelResponseEntity = hotelController.getOne(
                serviceDto.getHotelId());
        Hotel hotel = hotelResponseEntity.getBody();
        List<Hotel> list = new ArrayList<>();
        list.add(hotel);
        Service service = new Service(serviceDto.getName(), serviceDto.isAdditional(),
                serviceDto.getTotalPrice(), serviceDto.getDescription(), list);

        log.info("request for creating service with parameters {}", service);

        return new ResponseEntity<>(serviceRepo.save(service), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующем сервисе")
    public ResponseEntity<Service> update(@PathVariable("id") long id,
                                          @Valid @RequestBody ServiceDto serviceDto) {
        log.info("request for updating service from data source {}", serviceDto);
        ResponseEntity<Hotel> hotelResponseEntity = hotelController.getOne(
                serviceDto.getHotelId());
        Hotel hotel = hotelResponseEntity.getBody();
        Service service = new Service(serviceDto.getName(), serviceDto.isAdditional(),
                serviceDto.getTotalPrice(), serviceDto.getDescription(), null);

        log.info("request for updating service by id {} with parameters {}",
                id, service);

        Service serviceFromDataBase = serviceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found service with id = " + id));
        log.info("Service from data base: " + serviceFromDataBase);
        serviceFromDataBase.getHotels().add(hotel);
        BeanUtils.copyProperties(service, serviceFromDataBase,
                NullProperty.getNullPropertiesString(service));
        return new ResponseEntity<>(serviceRepo.save(serviceFromDataBase), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление сервиса по id")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Service service) {

        log.info("request for deleting service with parameters {}", service);

        serviceRepo.delete(service);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("Удаление всех сервисов")
    public ResponseEntity<HttpStatus> deleteAll() {
        log.info("request for deleting all services");
        serviceRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
