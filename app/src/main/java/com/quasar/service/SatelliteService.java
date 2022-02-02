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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class SatelliteService {

    @Inject
    private SatelliteRepository satelliteRepository;

    @Inject
    private CircleIntersectionCalculator circleIntersectionCalculator;


    @PostConstruct
    public void initSatellitesLocations() {

        saveLocation("kenobi", new Point(-500, -200));
        saveLocation("skywalker", new Point(100, -100));
        saveLocation("sato", new Point(500, 100));

    }

    /**
     * cleans the previously stored data
     */
    public void clearData() {
        satelliteRepository.clearData();
    }

    public void addSatelliteLocation() {

    }

    /**
     * Tries to find triangulate the location of the message using the info collected
     *
     * @return the triangulated location Point or null if the attempt has failed
     */

    public Point attemptToTriangulate() {
        List<Circle> allCircles = createCircles();

        if (allCircles.size() > 3) {
            // can't triangulate
            return null;
        }

        //try to triangulate
        Circle c0 = allCircles.get(0);
        Circle c1 = allCircles.get(1);
        Circle c2 = allCircles.get(2);

        List<Point> intersectionPoints01 = circleIntersectionCalculator.getIntersectionPoints(c0, c1);
        List<Point> intersectionPoints12 = circleIntersectionCalculator.getIntersectionPoints(c1, c2);

        // returns the common point or null (if location can't be triangulated)
        return Utils.getCommonPoint(intersectionPoints01, intersectionPoints12);

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
        satelliteRepository.putLocation(satelliteName.toLowerCase(), point);
    }

    public void addInterceptedMessage(InterceptedMessage interceptedMessage) {
        satelliteRepository.storeInterceptedMessage(interceptedMessage);
    }

    public Map<String, Point> getAllLocations() {
        return satelliteRepository.getLocations();
    }
}
