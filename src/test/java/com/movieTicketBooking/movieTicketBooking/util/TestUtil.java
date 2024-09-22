package com.movieTicketBooking.movieTicketBooking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import org.springframework.web.util.UriComponentsBuilder;

public class TestUtil {

  public static final ObjectMapper objectMapper = new ObjectMapper();
  public static final String BASE_PATH = "/theater/movie";

  public static <T> T readJsonFile(String fileName, Class<T> clazz) throws IOException {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ClassLoader classLoader = TestUtil.class.getClassLoader();
    File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    return objectMapper.readValue(file, clazz);
  }

  public static <T> String getJsonString(T t) throws JsonProcessingException {
    return objectMapper.writeValueAsString(t);
  }

  public static URI buildUri(String path, Object ...variables) {
    return UriComponentsBuilder.fromUriString(BASE_PATH + path)
        .buildAndExpand(variables)
        .toUri();
  }
}
