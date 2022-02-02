package com.quasar.rest;


import com.quasar.model.DecodedMessageAndLocation;
import com.quasar.rest.dto.TopSecretRequest;
import com.quasar.service.QuasarService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;


@Controller("/topsecret")
@Slf4j
public class TopSecretController {


    @Inject
    private QuasarService quasarService;


    @Post
    public DecodedMessageAndLocation post(@Body @Valid TopSecretRequest request) {
        log.debug("received POST: /topsecret {}", request);
        return quasarService.decodeMultipleMessages(request.getSatellites());
    }

}
