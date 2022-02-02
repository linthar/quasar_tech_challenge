package com.quasar;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
class QuasarTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testAppRun() {
        Assertions.assertTrue(application.isRunning());
    }

}
