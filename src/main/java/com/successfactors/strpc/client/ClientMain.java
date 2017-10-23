package com.successfactors.strpc.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.successfactors.strpc.model.JsonUtil;
import com.successfactors.strpc.model.PendingRequests;
import com.successfactors.strpc.model.Response;
import com.successfactors.strpc.model.ResponseParser;
import org.apache.commons.lang3.StringUtils;

public class ClientMain {
  private static int ri = 0;
  private static long start;
  private static Lock appLock = new ReentrantLock();

  public static void main(final String[] args) throws InterruptedException, URISyntaxException, JsonProcessingException {
    final RpcClientEndpoint clientEndPoint = new RpcClientEndpoint(new URI("ws://0.0.0.0:18080/rpc"));

    clientEndPoint.addMessageHandler(new RpcClientEndpoint.MessageHandler() {
      @Override
      public void handleMessage(String message) {
//				System.out.println(message);
        if (ri == 0) {
          if (!appLock.tryLock()) {
            System.out.println("Lock failure, should shutdown");
            throw new RuntimeException("Lock failure, should shutdown");
          }
        }
        try {
          Response response = ResponseParser.parse(message);
          PendingRequests.remove(response.getId());
        } catch (IOException e) {
          e.printStackTrace();
        }
        ri++;
        if (ri == 1000_000) {
          long cost = System.currentTimeMillis() - start;
          System.out.println(cost + " ms");
          appLock.unlock();
        }
      }
    });

    String[] params = {StringUtils.repeat('a', 100), StringUtils.repeat('b', 100)};
    JavaType returnType = JsonUtil.getTypeFactory().constructArrayType(String.class);

    start = System.currentTimeMillis();
    for (int i = 0; i < 1000_000; i++) {
      String id = String.valueOf(i);
      String request = id + ";echo;java.lang.String;java.lang.String\r\n" + JsonUtil.toJson(params);
      PendingRequests.putRequestInfo(id, returnType);
      clientEndPoint.sendMessage(request);
    }
    while (!PendingRequests.isEmpty()) {
      Thread.sleep(10);
    }
    appLock.lock(); // Wait
  }
}