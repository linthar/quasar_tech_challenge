package com.quasar.service;

import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import com.quasar.rest.dto.QuasarResponse;
import jakarta.inject.Singleton;

import java.util.List;

/**
 * Servicio que resuelve los requests de la API Rest
 * decodifica los mensajes y calcula las coordenadas del emisor
 */
@Singleton
public class QuasarService {

    public QuasarResponse decodeMultipleMessages(List<InterceptedMessage> interceptedMessages) {
        //TODO unmock this
        return new QuasarResponse(new Point(-100.23, 4.56), "Hello World");
    }


    public QuasarResponse decodeSigleMessage(InterceptedMessage interceptedMessage) {
        //TODO unmock this
        return new QuasarResponse(new Point(-100.23, 4.56), "Hello World");
    }
}
