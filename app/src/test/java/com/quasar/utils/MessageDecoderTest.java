package com.quasar.utils;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@MicronautTest
class MessageDecoderTest {

    @Inject
    private MessageDecoder messageDecoder;

    @Test
    void calculateNewDecodedMessage() {
        String[] prevDecodedMessage = new String[]{"este", "", "", "mensaje"};
        String[] newInterceptedMessage = new String[]{"", "es", "un", "mensaje"};
        String[] decoded = messageDecoder.calculateNewDecodedMessage(prevDecodedMessage, newInterceptedMessage);

        assertArrayEquals(new String[]{"este", "es", "un", "mensaje"}, decoded);
    }



    @Test
    void calculateNewDecodedMessageIncompleto() {
        String[] prevDecodedMessage = new String[]{"", "", "", "mensaje"};
        String[] newInterceptedMessage = new String[]{"", "es", "", "mensaje"};
        String[] decoded = messageDecoder.calculateNewDecodedMessage(prevDecodedMessage, newInterceptedMessage);

        assertArrayEquals(new String[]{"", "es", "", "mensaje"}, decoded);
    }


    @Test
    void calculateNewDecodedMessageDesfasado() {
        String[] prevDecodedMessage = new String[]{"este", "", "", "mensaje"};
        String[] newInterceptedMessage = new String[]{"", "", "", "es", "un", "mensaje"};
        String[] decoded = messageDecoder.calculateNewDecodedMessage(prevDecodedMessage, newInterceptedMessage);

        assertArrayEquals(new String[]{"este", "es", "un", "mensaje"}, decoded);
    }

    @Test
    void calculateNewDecodedMessageDesfasado2() {
        String[] prevDecodedMessage = new String[]{"", "", "este", "", "", "mensaje"};
        String[] newInterceptedMessage = new String[]{ "", "es", "un", "mensaje"};
        String[] decoded = messageDecoder.calculateNewDecodedMessage(prevDecodedMessage, newInterceptedMessage);

        assertArrayEquals(new String[]{"este", "es", "un", "mensaje"}, decoded);
    }



}

