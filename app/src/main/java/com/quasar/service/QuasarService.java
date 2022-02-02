package com.quasar.service;

import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import com.quasar.model.DecodedMessageAndLocation;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

/**
 * Servicio que resuelve los requests de la API Rest
 * decodifica los mensajes y calcula las coordenadas del emisor
 */
@Singleton
public class QuasarService {

    @Inject
    private SatelliteService satelliteService;
    @Inject
    private MessageService messageService;


    public DecodedMessageAndLocation decodeMultipleMessages(List<InterceptedMessage> interceptedMessages) {

        // borramos toda informacion colectada anteriormente
        satelliteService.clearData();
        messageService.clearData();

        for (InterceptedMessage interceptedMsg : interceptedMessages) {
            DecodedMessageAndLocation response = decodeSigleMessage(interceptedMsg);
            if (response != null) {
                // se pudo decodificar el mensaje y la ubicacion del emisor
                return response;
            }
        }
        // no se pudo decodificar el mensaje y la ubicacion del emisor
        // con la informacion recibida
        return null;
    }


    public DecodedMessageAndLocation decodeSigleMessage(InterceptedMessage interceptedMessage) {

        satelliteService.addInterceptedMessage(interceptedMessage);

        String decodedMessage = messageService.attemptMessageDecode(interceptedMessage.getMessage());
        if (decodedMessage == null){
            // todavia no se pudo decodificar el mensaje
            return null;
        }
        // else
        // se verifica si ya se encontro la ubicacion del emisor o no
        Point location = satelliteService.attemptToTriangulate();
        if (location == null){
            return null;
        }
        // si llegamos aca es porque se decodifico el mensage y la ubicacion
        return new DecodedMessageAndLocation(location, decodedMessage);
    }


}
