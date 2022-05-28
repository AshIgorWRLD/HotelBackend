package ru.nsu.ashikhmin.hotel_app;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.nsu.ashikhmin.hotel_app.controller.HotelController;
import ru.nsu.ashikhmin.hotel_app.entity.Hotel;
import ru.nsu.ashikhmin.hotel_app.repository.HotelRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(HotelController.class)
public class HotelControllerTests {

    @MockBean
    private HotelRepo hotelRepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Before("")
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(hotelRepo).build();
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfUsers() throws Exception {
        when(hotelRepo.findAll()).thenReturn(List.of(
                new Hotel("123", 5, 10, "123"),
                new Hotel("1234", 2, 3, "1233")));


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/hotels"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name",
                        Matchers.is("123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stars",
                        Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].floorsTotal",
                        Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description",
                        Matchers.is("123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name",
                        Matchers.is("1234")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stars",
                        Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].floorsTotal",
                        Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description",
                        Matchers.is("1233")));
    }
}
