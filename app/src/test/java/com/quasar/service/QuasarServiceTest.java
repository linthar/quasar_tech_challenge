package com.quasar.service;

import com.quasar.model.DecodedMessageAndLocation;
import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import com.quasar.persistence.SatelliteRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static com.quasar.service.SatelliteService.*;
@MicronautTest
class QuasarServiceTest {

    @Inject
    private SatelliteService satelliteService;
    @Inject
    private SatelliteRepository satelliteRepository;
    @Inject
    private MessageService messageService;
    @Inject
    private QuasarService quasarService;


    @BeforeEach
    public void setUp(){
        // reset collected data
        satelliteRepository.clearData();
        messageService.clearData();
    }

    @Test
    void cantDecodeSigleMessage() {

        // el mensaje se puede decodificar pero no se puede ubicar la locacion del emisor


        InterceptedMessage intercepted = new InterceptedMessage(SATELLITE_NAME_SATO, 100.0, new String[] {"", "2", "", "4"});
        DecodedMessageAndLocation decodedMessage = quasarService.decodeSigleMessage(intercepted);
        // message location can't be triangulated
        assertNull(decodedMessage);

        InterceptedMessage intercepted2 = new InterceptedMessage(SATELLITE_NAME_KENOBI, 30.0, new String[] {"1", "", "", "4"});
        DecodedMessageAndLocation decodedMessage2 = quasarService.decodeSigleMessage(intercepted);
        // message location can't be triangulated
        assertNull(decodedMessage2);

        InterceptedMessage intercepted3 = new InterceptedMessage(SATELLITE_NAME_SATO, 30.0, new String[] {"1", "", "3", "4"});
        DecodedMessageAndLocation decodedMessage3 = quasarService.decodeSigleMessage(intercepted);
        // message location can't be triangulated
        assertNull(decodedMessage3);
    }




    @Test
    void decodeSigleMessage() {

        // se ubican los satelites para que poder calcular la triangulacion
        satelliteRepository.putLocation(SATELLITE_NAME_KENOBI, 0.0, 10.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SKYWALKER, 10.0, 0.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SATO, 0.0, -10.0);

        InterceptedMessage intercepted1 = new InterceptedMessage(SATELLITE_NAME_SKYWALKER, 10.0, new String[] {"", "2", "", "4"});
        InterceptedMessage intercepted2 = new InterceptedMessage(SATELLITE_NAME_KENOBI, 10.0, new String[] {"1", "", "", "4"});
        InterceptedMessage intercepted3 = new InterceptedMessage(SATELLITE_NAME_SATO, 10.0, new String[] {"1", "", "3", "4"});


        // message location can't be triangulated with 1 message only
        DecodedMessageAndLocation decodedMessage = quasarService.decodeSigleMessage(intercepted1);
        assertNull(decodedMessage);

        // message location can't be triangulated with 2 messages
        DecodedMessageAndLocation decodedMessage2 = quasarService.decodeSigleMessage(intercepted2);
        assertNull(decodedMessage2);

        DecodedMessageAndLocation decodedMessage3 = quasarService.decodeSigleMessage(intercepted3);
        // message can be triangulated after 3rd message
        assertNotNull(decodedMessage3);
        assertEquals("1 2 3 4", decodedMessage3.getMessage());
        assertEquals(new Point(0.0, 0.0), decodedMessage3.getPosition());
    }




    @Test
    void decodeMultipleMessages() {
        // se ubican los satelites para que poder calcular la triangulacion
        satelliteRepository.putLocation(SATELLITE_NAME_KENOBI, 0.0, 10.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SKYWALKER, 10.0, 0.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SATO, 0.0, -10.0);

        InterceptedMessage intercepted1 = new InterceptedMessage(SATELLITE_NAME_SKYWALKER, 10.0, new String[] {"", "b", "", "d"});
        InterceptedMessage intercepted2 = new InterceptedMessage(SATELLITE_NAME_KENOBI, 10.0, new String[] {"a", "", "", "d"});
        InterceptedMessage intercepted3 = new InterceptedMessage(SATELLITE_NAME_SATO, 10.0, new String[] {"a", "", "c", "d"});


        DecodedMessageAndLocation decoded = quasarService.decodeMultipleMessages(Arrays.asList(intercepted1, intercepted2, intercepted3));
        // message can be triangulated
        assertNotNull(decoded);
        assertEquals("a b c d", decoded.getMessage());
        assertEquals(new Point(0.0, 0.0), decoded.getPosition());
    }



    @Test
    void decodeMultipleMessagesCantDecodeMessage() {

        // el mensaje no se puede decodificar porque faltan daltos (la segunda palabra nunca llega)

        // se ubican los satelites para que poder calcular la triangulacion
        satelliteRepository.putLocation(SATELLITE_NAME_KENOBI, 0.0, 10.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SKYWALKER, 10.0, 0.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SATO, 0.0, -10.0);

        InterceptedMessage intercepted1 = new InterceptedMessage(SATELLITE_NAME_SKYWALKER, 10.0, new String[] {"", "", "", "d"});
        InterceptedMessage intercepted2 = new InterceptedMessage(SATELLITE_NAME_KENOBI, 10.0, new String[] {"a", "", "", "d"});
        InterceptedMessage intercepted3 = new InterceptedMessage(SATELLITE_NAME_SATO, 10.0, new String[] {"a", "", "c", "d"});

        DecodedMessageAndLocation decoded = quasarService.decodeMultipleMessages(Arrays.asList(intercepted1, intercepted2, intercepted3));
        // message can not be decoded (b is missing in InterceptedMessage)
        assertNull(decoded);
    }


    @Test
    void decodeSigleMessage_NoInterceptionPoint() {

        // el mensaje se puede decodificar
        // pero los circulos no se intersectan
        // (todas las distancias tienen 1... deben tener 10 para que se intersecten)


        // se ubican los satelites para que poder calcular la triangulacion
        satelliteRepository.putLocation(SATELLITE_NAME_KENOBI, 0.0, 10.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SKYWALKER, 10.0, 0.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SATO, 0.0, -10.0);

        InterceptedMessage intercepted1 = new InterceptedMessage(SATELLITE_NAME_SKYWALKER, 1.0, new String[] {"", "2", "", "4"});
        InterceptedMessage intercepted2 = new InterceptedMessage(SATELLITE_NAME_KENOBI, 1.0, new String[] {"", "1", "", "", "4"});
        InterceptedMessage intercepted3 = new InterceptedMessage(SATELLITE_NAME_SATO, 1.0, new String[] {"1", "", "3", "4"});

        DecodedMessageAndLocation decoded = quasarService.decodeMultipleMessages(Arrays.asList(intercepted1, intercepted2, intercepted3));
        // message can not be decoded (b is missing in InterceptedMessage)
        assertNull(decoded);
    }

}