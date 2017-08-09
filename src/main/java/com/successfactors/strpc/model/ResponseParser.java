package com.successfactors.strpc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;

public class ResponseParser {
  public static Response parse(String message) throws IOException {
      JsonNode root = JsonUtil.readTree(message);
      String id = root.get("id").asText();
      JavaType returnType = PendingRequests.getRequestInfo(id);
      Object data = JsonUtil.treeToObject(root.get("data"), returnType);
      return new Response(id, data);
  }
}
