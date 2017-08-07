package com.successfactors.strpc.model;

public class ServiceClosure {
  public final ServiceLookup.ServiceFunction serviceFunction;
  public final Object[] params;

  public ServiceClosure(ServiceLookup.ServiceFunction serviceFunction, Object[] params) {
    this.serviceFunction = serviceFunction;
    this.params = params;
  }

  public Object call() throws Exception {
    return serviceFunction.call(params);
  }
}
