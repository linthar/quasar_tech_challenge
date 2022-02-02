package com.quasar.rest;

import com.quasar.service.QuasarService;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@MicronautTest
public abstract class AbstractRestControllerTest {
    @Inject
    @Client("/")
    protected HttpClient client;

    @MockBean(QuasarService.class)
    protected QuasarService quasarServiceMock() {
        return mock(QuasarService.class);
    }

    @Inject
    protected QuasarService quasarServiceMock;

    @AfterEach
    public void tearDown() {
        reset(quasarServiceMock);
    }


}
