package com.successfactors.strpc.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceLookup {
  private static Map<String, ServiceFunction> services = new ConcurrentHashMap<>();
  static {
    services.put("echo;java.lang.String;java.lang.String", new ServiceFunction() {
      @Override
      public Object call(Object[] params) throws Exception {
        return params;
      }

      @Override
      public Class<?>[] paramTypes() {
        return new Class[] {String.class, String.class};
      }
    });
  }

  public static ServiceFunction find(String name) {
    return services.get(name);
  }

  public interface ServiceFunction {
    Object call(Object[] params) throws Exception;

    Class<?>[] paramTypes();
  }
}
