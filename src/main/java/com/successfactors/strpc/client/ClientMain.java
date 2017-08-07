package com.successfactors.strpc.client;

import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.successfactors.strpc.model.JsonUtil;

public class ClientMain {
	public static void main(final String[] args) throws InterruptedException, URISyntaxException, JsonProcessingException {
		final RpcClientEndpoint clientEndPoint = new RpcClientEndpoint(new URI("ws://0.0.0.0:8080/rpc"));
		clientEndPoint.addMessageHandler(new RpcClientEndpoint.MessageHandler() {
			@Override
			public void handleMessage(String message) {
				System.out.println(message);
			}
		});

		while (true) {
		  String[] params = {"a", "b"};
		  String request = "echo;java.lang.String;java.lang.String\r\n" + JsonUtil.toJson(params);
			clientEndPoint.sendMessage(request);
		}
	}
}