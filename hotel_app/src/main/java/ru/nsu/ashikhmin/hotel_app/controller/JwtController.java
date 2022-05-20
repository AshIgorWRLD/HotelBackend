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
import ru.nsu.ashikhmin.hotel_app.dto.JwtDto;
import ru.nsu.ashikhmin.hotel_app.entity.Jwt;
import ru.nsu.ashikhmin.hotel_app.entity.User;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.JwtRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("jwt")
@Api(description = "Контроллер jwt ключей")
public class JwtController {

    private final JwtRepo jwtRepo;

    private final UserController userController;
    @Autowired
    public JwtController(JwtRepo jwtRepo, UserController userController) {
        this.jwtRepo = jwtRepo;
        this.userController = userController;
    }

    @GetMapping
    @ApiOperation("Получение списка jwt ключей")
    public ResponseEntity<List<Jwt>> list() {
        log.info("request for getting all jwts");
        List<Jwt> jwts = jwtRepo.findAll();
        if (jwts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(jwts, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    @ApiOperation("Получение jwt ключа по id")
    public ResponseEntity<Jwt> getOneById(@PathVariable("id") Long id) {
        log.info("request for getting jwt with id: {}", id);

        Jwt jwt = jwtRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found jwt with id = " + id));

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    @ApiOperation("Получение jwt ключа по id пользователя")
    public ResponseEntity<Jwt> getOneByUserId(@PathVariable("id") Long id) {
        log.info("request for getting jwt with user_id: {}", id);

        Jwt jwt = jwtRepo.findByUserId(id);
        if(jwt == null){
            throw new ResourceNotFoundException("Not found jwt with user_id = " + id);
        }

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание нового jwt ключа")
    public ResponseEntity<Jwt> create(@Valid @RequestBody JwtDto jwtDto) {
        log.info("request for creating jwt from data source {}", jwtDto);
        ResponseEntity<User> userResponseEntity = userController.getOne(
                jwtDto.getUserId());
        Jwt jwt = new Jwt(userResponseEntity.getBody(), jwtDto.getJwtToken());

        log.info("request for creating jwt with parameters {}", jwt);

        return new ResponseEntity<>(jwtRepo.save(jwt), HttpStatus.OK);
    }


    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующем jwt ключе")
    public ResponseEntity<Jwt> update(@PathVariable("id") long id,
                                       @Valid @RequestBody JwtDto jwtDto) {
        log.info("request for updating jwt from data source {}", jwtDto);
        ResponseEntity<User> userResponseEntity = userController.getOne(
                jwtDto.getUserId());
        Jwt jwt = new Jwt(userResponseEntity.getBody(), jwtDto.getJwtToken());

        log.info("request for updating jwt by id {} with parameters {}",
                id, jwt);

        Jwt jwtFromDataBase = jwtRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found jwt with id = " + id));
        log.info("Jwt from data base: " + jwtFromDataBase);

        BeanUtils.copyProperties(jwt, jwtFromDataBase,
                NullProperty.getNullPropertiesString(jwt));
        return new ResponseEntity<>(jwtRepo.save(jwtFromDataBase), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @ApiOperation("Удаление jwt ключа")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Jwt jwt) {

        log.info("request for deleting jwt with parameters {}", jwt);

        jwtRepo.delete(jwt);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("Удаление всех jwt ключей")
    public ResponseEntity<HttpStatus> deleteAll() {
        log.info("request for deleting all jwts");
        jwtRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
