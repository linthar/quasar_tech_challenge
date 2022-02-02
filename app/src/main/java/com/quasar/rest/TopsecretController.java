package com.quasar.rest;


import com.quasar.model.Point;
import com.quasar.rest.dto.QuasarResponse;
import com.quasar.rest.dto.TopSecretRequest;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;


@Controller("/topsecret")
@Slf4j
public class TopsecretController {

    @Post
    public QuasarResponse post(@Body @Valid TopSecretRequest request) {
        log.info("received topsecret POST: {}", request);

        return new QuasarResponse(new Point(-100.23, 4.56), "Hello World");
    }

}
