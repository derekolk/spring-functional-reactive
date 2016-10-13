package com.example;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.RxNettyHttpHandlerAdapter;
import org.springframework.web.reactive.function.RouterFunction;

import io.reactivex.netty.protocol.http.server.HttpServer;
import java.net.InetSocketAddress;

import static org.springframework.web.reactive.function.RouterFunctions.*;
import static org.springframework.web.reactive.function.RequestPredicates.*;

public class DemoApplication {

	public static final String HOST = "localhost";
	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		RouterFunction<?> route = routingFunction();

		HttpHandler httpHandler = toHttpHandler(route);

		// RxNetty
		RxNettyHttpHandlerAdapter adapter = new RxNettyHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.newServer(new InetSocketAddress(HOST, PORT));
		server.start(adapter).awaitShutdown();
	}

	public static RouterFunction<?> routingFunction() {
		DemoHandler handler = new DemoHandler();
		return route(GET("/hello-world"), handler::getDemo).and(route(GET("/wait"), handler::getDemoWait));
	}
}
