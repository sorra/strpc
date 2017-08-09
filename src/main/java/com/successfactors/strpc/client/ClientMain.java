package com.successfactors.strpc.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.successfactors.strpc.model.JsonUtil;
import org.apache.commons.lang3.StringUtils;

public class ClientMain {
	private static int ri = 0;
	private static long start;
	private static Lock lock = new ReentrantLock();

  public static void main(final String[] args) throws InterruptedException, URISyntaxException, JsonProcessingException {
		final RpcClientEndpoint clientEndPoint = new RpcClientEndpoint(new URI("ws://0.0.0.0:8080/rpc"));

		clientEndPoint.addMessageHandler(new RpcClientEndpoint.MessageHandler() {
			@Override
			public void handleMessage(String message) {
//				System.out.println(message);
        if (ri == 0) {
          if (!lock.tryLock()) {
            System.out.println("Lock failure, should shutdown");
            throw new RuntimeException("Lock failure, should shutdown");
          }
        }
        try {
          JsonUtil.readTree(message);
        } catch (IOException e) {
          e.printStackTrace();
        }
        ri++;
        if (ri == 1000_000) {
          long cost = System.currentTimeMillis() - start;
          System.out.println(cost + " ms");
          lock.unlock();
        }
			}
		});

		start = System.currentTimeMillis();
    String[] params = {StringUtils.repeat('a', 100), StringUtils.repeat('b', 100)};
    for (int i = 0; i < 1000_000; i++) {
			String request = i + ";echo;java.lang.String;java.lang.String\r\n" + JsonUtil.toJson(params);
			clientEndPoint.sendMessage(request);
		}
		lock.lock(); // Wait
  }
}