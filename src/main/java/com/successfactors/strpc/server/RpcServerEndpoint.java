package com.successfactors.strpc.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.successfactors.strpc.model.JsonUtil;
import com.successfactors.strpc.model.RequestParser;
import com.successfactors.strpc.model.Response;
import com.successfactors.strpc.model.ServiceClosure;

@ServerEndpoint("/rpc")
public class RpcServerEndpoint {

  @OnMessage
  public void onMessage(Session session, String message) {
    try {
//      System.out.println("onMessage:");
//      System.out.println(message);

      ServiceClosure serviceClosure = RequestParser.parse(message);
      Response response = new Response(serviceClosure.requestId, serviceClosure.call());
      session.getAsyncRemote().sendText(JsonUtil.toJson(response)).get(1, TimeUnit.MINUTES);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static final Queue<Session> QUEUE = new ConcurrentLinkedQueue<>();

  @OnOpen
  public void open(Session session) {
    System.out.println("open: " + session.getId());
    QUEUE.add(session);
  }

  @OnError
  public void error(Session session, Throwable t) {
    t.printStackTrace();
    QUEUE.remove(session);
  }

  @OnClose
  public void closedConnection(Session session) {
    System.out.println("close: " + session.getId());
    QUEUE.remove(session);
  }
}