package com.quasar.service;

import com.quasar.model.Circle;
import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import com.quasar.persistence.SatelliteRepository;
import com.quasar.utils.CircleIntersectionCalculator;
import com.quasar.utils.Utils;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
@Slf4j
public class SatelliteService {

    public static final String SATELLITE_NAME_KENOBI = "kenobi";
    public static final String SATELLITE_NAME_SKYWALKER = "skywalker";
    public static final String SATELLITE_NAME_SATO = "sato";

    @Inject
    private SatelliteRepository satelliteRepository;

    @Inject
    private CircleIntersectionCalculator circleIntersectionCalculator;


    @PostConstruct
    public void initSatellitesLocations() {
        saveLocation(SATELLITE_NAME_KENOBI, new Point(-500, -200));
        saveLocation(SATELLITE_NAME_SKYWALKER, new Point(100, -100));
        saveLocation(SATELLITE_NAME_SATO, new Point(500, 100));
    }

    /**
     * cleans the previously stored data
     */
    public void clearData() {
        satelliteRepository.clearData();
    }

    /**
     * Tries to find triangulate the location of the message using the info collected
     *
     * @return the triangulated location Point or null if the attempt has failed
     */

    public Point attemptToTriangulate() {
        log.debug("attempting to Triangulate using known points");
        List<Circle> allCircles = createCircles();

        log.debug("Circles created: {}", allCircles);
        if (allCircles.size() < 3) {
            log.debug("can't triangulate. Not enough points: {}", allCircles.size());
            // can't triangulate
            return null;
        }

        //try to triangulate
        Circle c0 = allCircles.get(0);
        Circle c1 = allCircles.get(1);
        Circle c2 = allCircles.get(2);

        List<Point> intersectionPoints01 = circleIntersectionCalculator.getIntersectionPoints(c0, c1);
        log.debug("intersectionPoints01: {}", intersectionPoints01);

        List<Point> intersectionPoints12 = circleIntersectionCalculator.getIntersectionPoints(c1, c2);
        log.debug("intersectionPoints12: {}", intersectionPoints12);

        // returns the common point or null (if location can't be triangulated)
        Point attempt = Utils.getCommonPoint(intersectionPoints01, intersectionPoints12);
        log.debug("attemptToTriangulate result: {}", attempt);
        return attempt;
    }


    private List<Circle> createCircles() {
        // create a circle for each InterceptedMessage
        List<Circle> allCircles = new ArrayList<Circle>();
        for (InterceptedMessage msg : satelliteRepository.getInterceptedMessages()) {
            String satName = msg.getName().toLowerCase();
            Point location = satelliteRepository.getLocation(satName);

            if (location == null){
                throw new RuntimeException("Can't find location for Satellite " +satName);
            }
            Circle c = new Circle(location, msg.getDistance());
            allCircles.add(c);
        }
        return allCircles;
    }


    public void saveLocation(String satelliteName, Point point) {
        log.debug("satellite {}. Saving location: {}", satelliteName, point);
        satelliteRepository.putLocation(satelliteName.toLowerCase(), point);
    }

    public void addInterceptedMessage(InterceptedMessage interceptedMessage) {
        log.debug("satellite {}. InterceptedMessage : {}", interceptedMessage.getName(), interceptedMessage);
        satelliteRepository.storeInterceptedMessage(interceptedMessage);
    }

    public Map<String, Point> getAllLocations() {
        return satelliteRepository.getLocations();
    }
}
