package ru.nsu.ashikhmin.hotel_app.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {
    public static String getJsonFileContent(ObjectMapper objectMapper, String filePath, Class<?> targetClass) throws IOException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Path pathToObject = Paths.get(filePath);
        File file = pathToObject.toFile();
        Object object = objectMapper.readValue(file, targetClass);

        return objectMapper.writeValueAsString(object);
    }
}