package com.quasar.rest;

import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import com.quasar.model.DecodedMessageAndLocation;
import com.quasar.rest.dto.TopSecretSplitRequest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopSecretSplitControllerTest extends AbstractRestControllerTest {

    final String ENDPOINT_URL = "/topsecret_split";

    @InjectMocks
    protected TopSecretSplitController topSecretSplitController;
    @Test
    void postWithResponse() {

        DecodedMessageAndLocation decodedMessageAndLocationMock = new DecodedMessageAndLocation(new Point(-100.23, 4.56), "Some Mock Test");
        TopSecretSplitRequest topSecretSplitRequest =
                new TopSecretSplitRequest(100d, new String[]{"message_word_0", "message_word_1", "message_word_2"});

        assertNotNull(quasarServiceMock);
        when(quasarServiceMock.decodeSigleMessage(any(InterceptedMessage.class))).thenReturn(decodedMessageAndLocationMock);


        URI uri = UriBuilder.of(ENDPOINT_URL + "/Kenobi").build();
        MutableHttpRequest request = HttpRequest.POST(uri, topSecretSplitRequest);
        HttpResponse<DecodedMessageAndLocation> httpResponse = client.toBlocking().exchange(request, DecodedMessageAndLocation.class);

        assertEquals(HttpStatus.OK, httpResponse.getStatus(), "response status is wrong");
        Optional<DecodedMessageAndLocation> oBody = httpResponse.getBody();
        assertTrue(oBody.isPresent(), "body is empty");

        DecodedMessageAndLocation response = oBody.get();
        assertEquals(decodedMessageAndLocationMock.getMessage(), response.getMessage());
        assertEquals(decodedMessageAndLocationMock.getPosition(), response.getPosition());
    }


    @Test
    void postWith404() {

        // Simula la situacion donde no hay un resultado claro a retornar
        // debe retornar 404 HTTP status
        when(quasarServiceMock.decodeSigleMessage(any(InterceptedMessage.class))).thenReturn(null);

        TopSecretSplitRequest topSecretSplitRequest =
                new TopSecretSplitRequest(100d, new String[]{"message_word_0", "message_word_1", "message_word_2"});

        URI uri = UriBuilder.of(ENDPOINT_URL + "/Kenobi").build();
        MutableHttpRequest request = HttpRequest.POST(uri, topSecretSplitRequest);


        try {
            client.toBlocking().exchange(request, DecodedMessageAndLocation.class);
            fail("A Not Found HttpClientResponseException must be thrown");
        } catch (HttpClientResponseException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }
}