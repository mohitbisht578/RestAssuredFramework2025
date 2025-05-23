package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, String> getJsonDataAsMap(String jsonFileName) {
        String completeJsonPath = System.getProperty("user.dirc") + "/src/test/resources";
        try {
           Map<String, String> data = objectMapper.readValue(new File(completeJsonPath), new TypeReference<Map<String, String>>() {});
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
