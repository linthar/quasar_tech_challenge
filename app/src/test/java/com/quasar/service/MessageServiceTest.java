package com.quasar.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class MessageServiceTest {

    @Inject
    private MessageService messageService;

    @BeforeEach
    public void setUp(){
        // reset collected data
        messageService.clearData();
    }

    @Test
    void getMessagefromTwo() {

        String[] m1 = new String[]{"este", "es",   "", "mensaje"};
        String[] m2 = new String[]{"",       "", "un", ""};

        String decoded1 = messageService.attemptMessageDecode(m1);
        // todavia no se puede decodificar el mensaje con la informacion actual
        assertNull(decoded1);


        String decoded2 = messageService.attemptMessageDecode(m2);
        // ahora si se puede decodificar
        assertEquals("este es un mensaje", decoded2);
    }

    @Test
    void getMessagefromThree() {

        String[] m1 = new String[]{"este", "", "","mensaje"};
        String[] m2 = new String[]{"", "", "un", ""};
        String[] m3 = new String[]{"", "es", "", "mensaje"};



        String decoded1 = messageService.attemptMessageDecode(m1);
        // todavia no se puede decodificar el mensaje con la informacion actual
        assertNull(decoded1);

        String decoded2 = messageService.attemptMessageDecode(m2);
        // todavia no se puede decodificar el mensaje con la informacion actual
        assertNull(decoded2);

        String decoded3 = messageService.attemptMessageDecode(m3);
        // ahora si se puede decodificar
        assertEquals("este es un mensaje", decoded3);
    }

    @Test
    void getMessageConDesfasajeInicial() {

        String[] m1 = new String[]{"", "este", "es", "un","mensaje"};
        String[] m2 = new String[]{"este", "", "un", "mensaje"};
        String[] m3 = new String[]{"", "", "es", "", "mensaje"};

        String decoded1 = messageService.attemptMessageDecode(m1);
        // todavia no se puede decodificar el mensaje con la informacion actual
        assertNull(decoded1);

        String decoded2 = messageService.attemptMessageDecode(m2);
        // ahora si se puede decodificar
        assertEquals("este es un mensaje", decoded2);

        String decoded3 = messageService.attemptMessageDecode(m3);
        // intentar agregar un mensaje mas no afecta el resultado obtenido
        assertEquals("este es un mensaje", decoded3);
    }
}