package com.successfactors.strpc.model;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;

public class RequestParser {
  public static ServiceClosure parse(String message) throws IOException {
    int idxOfDelimiter = message.indexOf("\r\n");
    if (idxOfDelimiter <= 0) {
      throw new RuntimeException("Request has no header");
    }
    String name = message.substring(0, idxOfDelimiter);
    String dataJson = message.substring(idxOfDelimiter + 2);

    ServiceLookup.ServiceFunction serviceFunction = ServiceLookup.find(name);
    Class<?>[] paramTypes = serviceFunction.paramTypes();

    Object[] params = new Object[paramTypes.length];

    Iterator<JsonNode> iterator = JsonUtil.objectMapper.readTree(dataJson).iterator();
    for (int i = 0; i < paramTypes.length; i++) {
      JsonNode paramNode = iterator.next();
      params[i] = JsonUtil.objectMapper.treeToValue(paramNode, paramTypes[i]);
    }

    return new ServiceClosure(serviceFunction, params);
  }
}
