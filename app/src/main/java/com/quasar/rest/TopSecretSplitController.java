package com.quasar.rest;


import com.quasar.model.InterceptedMessage;
import com.quasar.model.DecodedMessageAndLocation;
import com.quasar.rest.dto.TopSecretSplitRequest;
import com.quasar.service.QuasarService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Controller("/topsecret_split")
@Slf4j
public class TopSecretSplitController {

    @Inject
    private QuasarService quasarService;

    @Post("/{satelliteName}")
    public DecodedMessageAndLocation post(@PathVariable(value = "satelliteName") @NotNull String satelliteName,
                                          @Body @Valid TopSecretSplitRequest request) {
        log.debug("received POST: /topsecret_split/{} {}", satelliteName, request);
        InterceptedMessage msg = new InterceptedMessage(satelliteName, request.getDistance(), request.getMessage());
        return quasarService.decodeSigleMessage(msg);
    }

}
