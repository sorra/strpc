package com.successfactors.strpc.model;

public class Response {
  Object data;
  Throwable throwable;

  public Response(Object data) {
    this.data = data;
  }

  public Response(Throwable throwable) {
    this.throwable = throwable;
  }

  public Object getData() {
    return data;
  }

  public Throwable getThrowable() {
    return throwable;
  }
}
