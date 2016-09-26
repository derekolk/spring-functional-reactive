package com.example;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.Response;
import org.springframework.web.reactive.function.RouterFunction;
import reactor.ipc.netty.http.HttpServer;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.util.Optional;

import static org.springframework.web.reactive.function.RouterFunctions.toHttpHandler;

public class DemoApplication {

	public static final String HOST = "localhost";
	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		RouterFunction<?> route = routingFunction();

		HttpHandler httpHandler = toHttpHandler(route);

		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.create(HOST, PORT);
		server.startAndAwait(adapter);
	}

	public static RouterFunction<?> routingFunction() {
		RouterFunction<String> helloWorldRoute = request -> {
			if (request.path().equals("/hello-world")) {
				return Optional.of(r -> Response.ok().body(fromObject("Hello World")));
			} else {
				return Optional.empty();
			}
		};

		return helloWorldRoute;
	}
}
