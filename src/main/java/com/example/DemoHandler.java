package com.example;


import org.reactivestreams.Publisher;
import org.springframework.web.reactive.function.Request;
import org.springframework.web.reactive.function.Response;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

public class DemoHandler {

    public Response<Publisher<Demo>> getDemo(Request request) {
        return Response.ok().body(fromPublisher(Mono.just(new Demo("hello-world")), Demo.class));
    }

    public Response<Publisher<Demo>> getDemoWait(Request request) {
        Mono<Demo> demoMono = Mono.fromCallable(() -> {
            Thread.sleep(500);
            return new Demo("ratpack-killa");
        }).subscribeOn(Schedulers.elastic());

        return Response.ok().body(fromPublisher(demoMono, Demo.class));
    }
}
