package com.quasar.rest;

import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import com.quasar.rest.dto.QuasarResponse;
import com.quasar.rest.dto.TopSecretRequest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


class TopSecretControllerTest extends AbstractRestControllerTest {

    final String ENDPOINT_URL = "/topsecret";

    @InjectMocks
    protected TopSecretController topsecretController;

    @Test
    void postWithResponse() {

        QuasarResponse quasarResponseMock = new QuasarResponse(new Point(-100.23, 4.56), "Some Mock Test");
        when(quasarServiceMock.decodeMultipleMessages(any())).thenReturn(quasarResponseMock);

        List<InterceptedMessage> satellites = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InterceptedMessage msg = new InterceptedMessage("satellite_" + i,
                    100d + i, new String[]{"message_word_0_" + i, "message_word__" + i, "message_word_2_" + i,});
            satellites.add(msg);
        }

        TopSecretRequest topSecretRequest = new TopSecretRequest(satellites);

        URI uri = UriBuilder.of(ENDPOINT_URL).build();
        MutableHttpRequest request = HttpRequest.POST(uri, topSecretRequest);
        HttpResponse<QuasarResponse> httpResponse = client.toBlocking().exchange(request, QuasarResponse.class);


        assertEquals(HttpStatus.OK, httpResponse.getStatus(), "response status is wrong");
        Optional<QuasarResponse> oBody = httpResponse.getBody();
        assertTrue(oBody.isPresent(), "body is empty");

        QuasarResponse response = oBody.get();
        assertEquals(quasarResponseMock.getMessage(), response.getMessage());
        assertEquals(quasarResponseMock.getPosition(), response.getPosition());
    }


    @Test
    void postWith404() {

        // Simula la situacion donde no hay un resultado claro a retornar
        // debe retornar 404 HTTP status
        when(quasarServiceMock.decodeMultipleMessages(any())).thenReturn(null);

        List<InterceptedMessage> satellites = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InterceptedMessage msg = new InterceptedMessage("satellite_" + i,
                    100d + i, new String[]{"message_word_0_" + i, "message_word__" + i, "message_word_2_" + i,});
            satellites.add(msg);
        }

        TopSecretRequest topSecretRequest = new TopSecretRequest(satellites);

        URI uri = UriBuilder.of(ENDPOINT_URL).build();
        MutableHttpRequest request = HttpRequest.POST(uri, topSecretRequest);


        try {
            client.toBlocking().exchange(request, QuasarResponse.class);
            fail("A Not Found HttpClientResponseException must be thrown");
        } catch (HttpClientResponseException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }
}