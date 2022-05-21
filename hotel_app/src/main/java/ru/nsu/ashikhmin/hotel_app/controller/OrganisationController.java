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
import ru.nsu.ashikhmin.hotel_app.entity.Organisation;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.OrganisationRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("organisations")
@Api(description = "Контроллер организаций")
public class OrganisationController {

    private final OrganisationRepo organisationRepo;

    @Autowired
    public OrganisationController(OrganisationRepo organisationRepo) {
        this.organisationRepo = organisationRepo;
    }

    @GetMapping
    @ApiOperation("Получение списка организаций")
    public ResponseEntity<List<Organisation>> list() {
        log.info("request for getting all organisations");
        List<Organisation> organisations = organisationRepo.findAll();
        if (organisations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(organisations, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @ApiOperation("Получение организации по id")
    public ResponseEntity<Organisation> getOneById(@PathVariable("id") Long id) {
        log.info("request for getting organisation with id: {}", id);

        Organisation organisation = organisationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found organisation with id = " + id));

        return new ResponseEntity<>(organisation, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @ApiOperation("Получение организации по названию")
    public ResponseEntity<Organisation> getOneByName(@PathVariable("name") String name) {
        log.info("request for getting organisation with name: {}", name);

        Organisation organisation = organisationRepo.findByName(name);
        if (organisation == null) {
            throw new ResourceNotFoundException("Not found organisation with name = " + name);
        }

        return new ResponseEntity<>(organisation, HttpStatus.OK);
    }

    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание новой организации")
    public ResponseEntity<Organisation> create(@Valid @RequestBody Organisation organisation) {
        log.info("request for creating person with parameters {}", organisation);

        return new ResponseEntity<>(organisationRepo.save(organisation), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующей организации")
    public ResponseEntity<Organisation> update(@PathVariable("id") long id,
                                               @Valid @RequestBody Organisation organisation) {
        log.info("request for updating user by id {} with parameters {}",
                id, organisation);

        Organisation organisationFromDataBase = organisationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found user with id = " + id));
        log.info("User from data base: " + organisationFromDataBase);

        BeanUtils.copyProperties(organisation, organisationFromDataBase,
                NullProperty.getNullPropertiesString(organisation));
        return new ResponseEntity<>(organisationRepo.save(organisationFromDataBase), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление организации")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Organisation organisation) {

        log.info("request for deleting organisation with parameters {}", organisation);

        organisationRepo.delete(organisation);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("Удаление всех организаций")
    public ResponseEntity<HttpStatus> deleteAll() {
        log.info("request for deleting all users");
        organisationRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
