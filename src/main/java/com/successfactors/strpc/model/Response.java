package com.successfactors.strpc.model;

public class Response {
  String id;
  Object data;
  String errorMsg;

  Response() {}

  public Response(String id, Object data) {
    this.id = id;
    this.data = data;
  }

  public Response(String id, Throwable throwable) {
    this.id = id;
    this.errorMsg = throwable.toString();
  }

  public String getId() {
    return id;
  }

  public Object getData() {
    return data;
  }

  public String getErrorMsg() {
    return errorMsg;
  }
}
