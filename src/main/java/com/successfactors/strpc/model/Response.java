package com.successfactors.strpc.model;

public class Response {
  String id;
  Object data;
  Throwable throwable;

  public Response(String id, Object data) {
    this.id = id;
    this.data = data;
  }

  public Response(String id, Throwable throwable) {
    this.id = id;
    this.throwable = throwable;
  }

  public String getId() {
    return id;
  }

  public Object getData() {
    return data;
  }

  public Throwable getThrowable() {
    return throwable;
  }
}
