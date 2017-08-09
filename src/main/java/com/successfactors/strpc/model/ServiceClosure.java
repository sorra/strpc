package com.successfactors.strpc.model;

public class ServiceClosure {
  public final String requestId;
  public final ServiceLookup.ServiceFunction serviceFunction;
  public final Object[] params;

  public ServiceClosure(String requestId, ServiceLookup.ServiceFunction serviceFunction, Object[] params) {
    this.requestId = requestId;
    this.serviceFunction = serviceFunction;
    this.params = params;
  }

  public Object call() throws Exception {
    return serviceFunction.call(params);
  }
}
