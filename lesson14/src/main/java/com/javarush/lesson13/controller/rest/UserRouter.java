package com.javarush.lesson13.controller.rest;

import com.javarush.lesson13.map.Mapper;
import com.javarush.lesson13.repository.Repo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {

    public static final String ROUTER_ENDPOINT_USERS = "/restapi/v2/users";
    public static final String USER_ID_PLACEHOLDER = "/{id:[0-9]+}";

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler, Repo repo) {
        return RouterFunctions
                .route(
                        GET(ROUTER_ENDPOINT_USERS + USER_ID_PLACEHOLDER).and(accept(APPLICATION_JSON)),
                        serverRequest -> repo
                                .findById(getId(serverRequest))
                                .map(Mapper::map)
                                .flatMap(user -> ServerResponse.ok().body(fromValue(user)))
                                .switchIfEmpty(ServerResponse.notFound().build()))
                .andRoute(
                        GET(ROUTER_ENDPOINT_USERS)
                                .and(accept(APPLICATION_JSON)), userHandler::listUsers)
                .andRoute(
                        POST(ROUTER_ENDPOINT_USERS)
                                .and(accept(APPLICATION_JSON)), userHandler::addNewUser)
                .andRoute(
                        PUT(ROUTER_ENDPOINT_USERS + USER_ID_PLACEHOLDER)
                                .and(accept(APPLICATION_JSON)), userHandler::updateUser)
                .andRoute(
                        DELETE(ROUTER_ENDPOINT_USERS + USER_ID_PLACEHOLDER)
                                .and(accept(APPLICATION_JSON)), userHandler::deleteUser);
    }

    private static long getId(ServerRequest serverRequest) {
        return Long.parseLong(serverRequest.pathVariable("id"));
    }
}