package com.successfactors.strpc.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  static ObjectMapper objectMapper = new ObjectMapper();

  public static String toJson(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <T> T toObject(String json, Class<T> cls) throws IOException {
    return objectMapper.readValue(json, cls);
  }
}
