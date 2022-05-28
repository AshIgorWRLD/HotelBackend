package ru.nsu.ashikhmin.hotel_app;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.nsu.ashikhmin.hotel_app.controller.UserController;
import ru.nsu.ashikhmin.hotel_app.entity.Role;
import ru.nsu.ashikhmin.hotel_app.entity.User;
import ru.nsu.ashikhmin.hotel_app.repository.OrganisationRepo;
import ru.nsu.ashikhmin.hotel_app.repository.RoleRepo;
import ru.nsu.ashikhmin.hotel_app.repository.UserRepo;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private OrganisationRepo organisationRepo;

    @MockBean
    private RoleRepo roleRepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Before("")
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRepo,
                organisationRepo, roleRepo).build();
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfUsers() throws Exception {
        when(userRepo.findAll()).thenReturn(List.of(
                new User("kobtsev.info@gmail.com", "123qwerty", "kobtsev.info@gmail.com",
                        "Michael", "Kobtsev", new Role("system_admin"),
                        new Timestamp(2022, 5, 27, 18, 40, 13, 0),
                        new Timestamp(2022, 5, 27, 18, 40, 16, 0),
                        null),
                new User("lew81@yahoo.com", "12345678", "lew81@yahoo.com",
                        "Kenneth", "Pierce", new Role("client"),
                        new Timestamp(2022, 5, 27, 18, 42, 55, 0),
                        new Timestamp(2022, 5, 27, 18, 43, 0, 0),
                        null)));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].login",
                        Matchers.is("kobtsev.info@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password",
                        Matchers.is("123qwerty")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].login",
                        Matchers.is("lew81@yahoo.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password",
                        Matchers.is("12345678")));
    }

    @Test
    void shouldGetUserByLoginAndPassword() throws Exception {
        when(userRepo.findByLoginAndPassword("kobtsev.info@gmail.com", "123qwerty"))
                .thenReturn(
                        java.util.Optional.of(new User("kobtsev.info@gmail.com", "123qwerty", "kobtsev.info@gmail.com",
                                "Michael", "Kobtsev", new Role("system_admin"),
                                new Timestamp(2022, 5, 27, 18, 40, 13, 0),
                                new Timestamp(2022, 5, 27, 18, 40, 16, 0),
                                null)));
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/users/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\": \"kobtsev.info@gmail.com\",\n" +
                                "    \"password\": \"123qwerty\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login",
                        Matchers.is("kobtsev.info@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password",
                        Matchers.is("123qwerty")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        Matchers.is("kobtsev.info@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is("Michael")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        Matchers.is("Kobtsev")));
    }
}
