package com.quasar.service;

import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import com.quasar.persistence.SatelliteRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class SatelliteServiceTest {

    public static final String SATELLITE_NAME_KENOBI = "kenobi";
    public static final String SATELLITE_NAME_SKYWALKER = "skywalker";
    public static final String SATELLITE_NAME_SATO = "sato";
    @Inject
    private SatelliteService satelliteService;

    @Inject
    private SatelliteRepository satelliteRepository;

    @BeforeEach
    public void setUp(){
        // reset collected data
        satelliteRepository.clearData();
    }

    @Test
    void attemptToTriangulateWithNoData() {
        // no messages were intercepted
        Point ubicacion = satelliteService.attemptToTriangulate();

        // location can't be triangulated
        assertNull(ubicacion);
    }

    @Test
    void attemptToTriangulateWithOneMessage() {
        // only 1 message was intercepted
        storeInterceptedMessage(SATELLITE_NAME_KENOBI, 8.0);

        Point ubicacion = satelliteService.attemptToTriangulate();
        // location can't be triangulated
        assertNull(ubicacion);
    }


    @Test
    void attemptToTriangulateWithTwoMessage() {
        // only 2 message was intercepted
        storeInterceptedMessage(SATELLITE_NAME_KENOBI, 10.5);
        storeInterceptedMessage(SATELLITE_NAME_SATO, 100.11);

        Point ubicacion = satelliteService.attemptToTriangulate();
        // location can't be triangulated
        assertNull(ubicacion);
    }



    @Test
    void cantTriangulate() {
        // 3 message was intercepted
        storeInterceptedMessage(SATELLITE_NAME_KENOBI, 20);
        storeInterceptedMessage(SATELLITE_NAME_SATO, 10);
        storeInterceptedMessage(SATELLITE_NAME_SKYWALKER, 30);

        Point ubicacion = satelliteService.attemptToTriangulate();
        // location can't be triangulated
        assertNull(ubicacion);
    }


    @Test
    void attemptToTriangulateOk() {
        // se ubican los satelites para que poder calcular la triangulacion
        satelliteRepository.putLocation(SATELLITE_NAME_KENOBI, 0.0, 10.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SKYWALKER, 10.0, 0.0);
        satelliteRepository.putLocation(SATELLITE_NAME_SATO, 0.0, -10.0);

        // 3 message was intercepted
        storeInterceptedMessage(SATELLITE_NAME_KENOBI, 10.0);
        storeInterceptedMessage(SATELLITE_NAME_SATO, 10.0);
        storeInterceptedMessage(SATELLITE_NAME_SKYWALKER, 10.0);

        Point ubicacion = satelliteService.attemptToTriangulate();
        //  triangulated location mus be 0,0
        assertEquals(new Point(0.0, 0.0), ubicacion);
    }




    private InterceptedMessage storeInterceptedMessage (String satName, double distance){
       String[] fakeMessage = new String[] {"fake", "message"};
        InterceptedMessage intercepted = new InterceptedMessage(satName, distance, fakeMessage);
        satelliteService.addInterceptedMessage(intercepted);
        return intercepted;
    }
}