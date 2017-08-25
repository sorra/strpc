package com.successfactors.strpc.client;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.websocket.*;

@ClientEndpoint
public class RpcClientEndpoint {
  private Session session = null;
  private MessageHandler messageHandler;

  public RpcClientEndpoint(final URI endpointURI) {
    try {
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      container.connectToServer(this, endpointURI);
    } catch (DeploymentException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @OnOpen
  public void onOpen(final Session session) {
    this.session = session;
  }

  @OnClose
  public void onClose(final Session session, final CloseReason reason) {
    this.session = null;
  }

  @OnMessage
  public void onMessage(final String message) {
    if (messageHandler != null) {
      messageHandler.handleMessage(message);
    }
  }

  public void addMessageHandler(final MessageHandler msgHandler) {
    messageHandler = msgHandler;
  }

  public void sendMessage(final String message) {
    try {
      session.getAsyncRemote().sendText(message).get(1, TimeUnit.MINUTES);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new RuntimeException(e);
    }
  }

  public interface MessageHandler {
    void handleMessage(String message);
  }
}