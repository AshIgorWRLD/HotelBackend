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
import ru.nsu.ashikhmin.hotel_app.dto.UserDto;
import ru.nsu.ashikhmin.hotel_app.dto.UserVerificationDto;
import ru.nsu.ashikhmin.hotel_app.entity.Organisation;
import ru.nsu.ashikhmin.hotel_app.entity.Role;
import ru.nsu.ashikhmin.hotel_app.entity.User;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.OrganisationRepo;
import ru.nsu.ashikhmin.hotel_app.repository.RoleRepo;
import ru.nsu.ashikhmin.hotel_app.repository.UserRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("users")
@Api(description = "Контроллер пользователей")
public class UserController {

    private final UserRepo userRepo;

    private final OrganisationRepo organisationRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public UserController(UserRepo userRepo, OrganisationRepo organisationRepo,
                          RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.organisationRepo = organisationRepo;
        this.roleRepo = roleRepo;
    }

    @GetMapping
    @ApiOperation("Получение списка пользователей")
    public ResponseEntity<List<User>> list() {
        log.info("request for getting all users");
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    @ApiOperation("Получение пользователя по id")
    public ResponseEntity<User> getOne(@PathVariable("id") Long id) {
        log.info("request for getting user with id: {}", id);

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found user with id = " + id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("get")
    @ApiOperation("Получение пользователя по логину и паролю")
    public ResponseEntity<User> getOne(@Valid @RequestBody UserVerificationDto userVerificationDto) {
        log.info("request for getting user with data {}", userVerificationDto);

        User user = userRepo.findByLoginAndPassword(userVerificationDto.getLogin(),
                userVerificationDto.getPassword()).orElseThrow(() ->
                new ResourceNotFoundException("Not found user with login = " + userVerificationDto.getLogin() +
                        " and password = " + userVerificationDto.getPassword()));


        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание нового пользователя")
    public ResponseEntity<User> create(@Valid @RequestBody UserDto userDto) {
        log.info("request for creating user from data source {}", userDto);
        Organisation organisation = null;
        if (userDto.getOrganisationId() != null) {
            organisation = organisationRepo.findById(
                    userDto.getOrganisationId()).orElseThrow(() -> new ResourceNotFoundException(
                    "Not found organisation with id = " + userDto.getOrganisationId()));
        }
        Role role = roleRepo.findByName(
                userDto.getRole()).orElseThrow(() -> new ResourceNotFoundException(
                "Not found role with name = " + userDto.getRole()));
        User user = new User(userDto.getLogin(), userDto.getPassword(), userDto.getEmail(),
                userDto.getFirstName(), userDto.getLastName(), role,
                userDto.getCreatedAt(), userDto.getUpdatedAt(), organisation);

        log.info("request for creating user with parameters {}", user);

        return new ResponseEntity<>(userRepo.save(user), HttpStatus.OK);
    }


    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующем пользователе")
    public ResponseEntity<User> update(@PathVariable("id") long id,
                                       @Valid @RequestBody UserDto userDto) {
        log.info("request for updating user from data source {}", userDto);
        Organisation organisation = null;
        if (userDto.getOrganisationId() != null) {
            organisation = organisationRepo.findById(
                    userDto.getOrganisationId()).orElseThrow(() -> new ResourceNotFoundException(
                    "Not found organisation with id = " + userDto.getOrganisationId()));
        }
        Role role = roleRepo.findByName(
                userDto.getRole()).orElseThrow(() -> new ResourceNotFoundException(
                "Not found role with name = " + userDto.getRole()));
        User user = new User(userDto.getLogin(), userDto.getPassword(), userDto.getEmail(),
                userDto.getFirstName(), userDto.getLastName(), role,
                userDto.getCreatedAt(), userDto.getUpdatedAt(), organisation);
        log.info("request for updating user by id {} with parameters {}",
                id, user);

        User userFromDataBase = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found user with id = " + id));
        log.info("User from data base: " + userFromDataBase);

        BeanUtils.copyProperties(user, userFromDataBase,
                NullProperty.getNullPropertiesString(user));
        return new ResponseEntity<>(userRepo.save(userFromDataBase), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @ApiOperation("Удаление пользователя")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") User user) {

        log.info("request for deleting user with parameters {}", user);

        userRepo.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("Удаление всех пользователей")
    public ResponseEntity<HttpStatus> deleteAll() {
        log.info("request for deleting all users");
        userRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
