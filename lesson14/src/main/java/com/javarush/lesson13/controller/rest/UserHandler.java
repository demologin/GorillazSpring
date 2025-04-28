package com.javarush.lesson13.controller.rest;

import com.javarush.lesson13.dto.UserTo;
import com.javarush.lesson13.map.Mapper;
import com.javarush.lesson13.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }


    private static long getId(ServerRequest serverRequest) {
        return Long.parseLong(serverRequest.pathVariable("id"));
    }

    public Mono<ServerResponse> listUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findAll(), UserTo.class);
    }

    public Mono<ServerResponse> addNewUser(ServerRequest serverRequest) {
        Mono<UserTo> userMono = serverRequest.bodyToMono(UserTo.class);
        return userMono.flatMap(user ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.save(user), UserTo.class));

    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        final long userId = getId(serverRequest);
        Mono<UserTo> userMono = serverRequest.bodyToMono(UserTo.class);

        return userMono.flatMap(user ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.save(user), UserTo.class));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        final long userId = getId(serverRequest);
        return userService
                .get(userId)
                .flatMap(s -> ServerResponse.noContent().build(userService.delete(s)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}