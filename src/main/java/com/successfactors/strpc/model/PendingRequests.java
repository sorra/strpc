package com.successfactors.strpc.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.JavaType;

public class PendingRequests {
  private static Map<String, JavaType> idVsInfoMap = new ConcurrentHashMap<>();

  public static JavaType getRequestInfo(String id) {
    return idVsInfoMap.get(id);
  }

  public static void putRequestInfo(String id, JavaType returnType) {
    idVsInfoMap.put(id, returnType);
  }

  public static void remove(String id) {
    idVsInfoMap.remove(id);
  }
  
  public static boolean isEmpty() {
    return idVsInfoMap.isEmpty();
  }
}
