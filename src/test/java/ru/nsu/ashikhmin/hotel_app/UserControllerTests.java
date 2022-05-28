package ru.nsu.ashikhmin.hotel_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.nsu.ashikhmin.hotel_app.dto.UserDto;
import ru.nsu.ashikhmin.hotel_app.entity.User;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.nsu.ashikhmin.hotel_app.utils.TestUtils.getJsonFileContent;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String USER_REQUEST_PATH = "src/test/resources/json/users/request/";
    private static final String USER_RESPONSE_PATH = "src/test/resources/json/users/response/";

    @Test
    @Sql(value = {"/sql/util/clear-db.sql"})
    @Sql(value = {"/sql/util/clear-db.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createGroupDefault() throws Exception {
        Path pathToGroup = Paths.get(USER_REQUEST_PATH + "user-list-dto.json");
        File file = pathToGroup.toFile();

        List<UserDto> userDto = Arrays.stream(objectMapper.readValue(file, UserDto[].class)).toList();
        String groupJson = objectMapper.writeValueAsString(userDto);
        String expectedJsonContent = getJsonFileContent(objectMapper, USER_RESPONSE_PATH + "user-list-default.json",
                User[].class);

        System.out.println(groupJson);
        System.out.println(expectedJsonContent);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(groupJson)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

}
