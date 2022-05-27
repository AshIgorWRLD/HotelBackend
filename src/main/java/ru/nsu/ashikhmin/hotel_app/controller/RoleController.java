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
import ru.nsu.ashikhmin.hotel_app.entity.Role;
import ru.nsu.ashikhmin.hotel_app.exceptions.ResourceNotFoundException;
import ru.nsu.ashikhmin.hotel_app.repository.RoleRepo;
import ru.nsu.ashikhmin.hotel_app.utils.NullProperty;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("users/roles")
@Api(description = "Контроллер ролей")
public class RoleController {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleController(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @GetMapping
    @ApiOperation("Получение списка ролей")
    public ResponseEntity<List<Role>> list() {
        log.info("request for getting all roles");
        List<Role> roles = roleRepo.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    @ApiOperation("Получение роли по id")
    public ResponseEntity<Role> getOneById(@PathVariable("id") Long id) {
        log.info("request for getting role with id: {}", id);

        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found role with id = " + id));

        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("name/{name}")
    @ApiOperation("Получение роли по названию")
    public ResponseEntity<Role> getOneByName(@PathVariable("name") String name) {
        log.info("request for getting role with name: {}", name);

        Role role = roleRepo.findByName(name);
        if (role == null) {
            throw new ResourceNotFoundException(
                    "Not found role with name = " + name);
        }

        return new ResponseEntity<>(role, HttpStatus.OK);
    }


    @PostMapping(consumes = {"*/*"})
    @ApiOperation("Создание новой роли")
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
        log.info("request for creating role with parameters {}", role);

        return new ResponseEntity<>(roleRepo.save(role), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ApiOperation("Обновление информации о существующей роли")
    public ResponseEntity<Role> update(@PathVariable("id") long id,
                                       @Valid @RequestBody Role role) {
        log.info("request for updating role by id {} with parameters {}",
                id, role);
        Role roleFromDataBase = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found role with id = " + id));
        log.info("Role from data base: " + roleFromDataBase);
        BeanUtils.copyProperties(role, roleFromDataBase,
                NullProperty.getNullPropertiesString(role));
        return new ResponseEntity<>(roleRepo.save(roleFromDataBase), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление роли")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Role role) {

        log.info("request for deleting role with parameters {}", role);

        roleRepo.delete(role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("Удаление всех ролей")
    public ResponseEntity<HttpStatus> deleteAll() {
        log.info("request for deleting all roles");
        roleRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
