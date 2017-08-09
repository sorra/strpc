package com.successfactors.strpc.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonUtil {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static String toJson(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <T> T toObject(String json, Class<T> cls) throws IOException {
    return objectMapper.readValue(json, cls);
  }

  public static JsonNode readTree(String json) throws IOException {
    return objectMapper.readTree(json);
  }

  public static <T> T treeToObject(JsonNode tree, Class<T> cls) throws JsonProcessingException {
    return objectMapper.treeToValue(tree, cls);
  }

  public static <T> T treeToObject(JsonNode tree, JavaType type) throws IOException {
    return objectMapper.readValue(objectMapper.treeAsTokens(tree), type);
  }

  public static TypeFactory getTypeFactory() {
    return objectMapper.getTypeFactory();
  }
}
