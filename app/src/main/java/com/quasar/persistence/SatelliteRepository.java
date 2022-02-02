package com.quasar.persistence;

import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SatelliteRepository {

    // se guardan las locations en un "storage en memoria"
    // (solo para este Tech challenge)
    HashMap<String, Point> locations = new HashMap<>();
    private HashMap<String, InterceptedMessage> interceptedMessageBySatellite = new HashMap<>();

    public Map<String, Point> getLocations() {
        return  Collections.unmodifiableMap(locations);
    }

    public Point getLocation(String satelliteName) {
        return locations.get(satelliteName);
    }

    public void putLocation(String satelliteName, double x, double y) {
        putLocation(satelliteName, new Point(x, y));
    }

    public void putLocation(String satelliteName, Point point) {
        locations.put(satelliteName, point);
    }

    public void storeInterceptedMessage(InterceptedMessage interceptedMsg) {
        interceptedMessageBySatellite.put(interceptedMsg.getName().toLowerCase(), interceptedMsg);
    }

    public Collection<InterceptedMessage> getInterceptedMessages() {
        return interceptedMessageBySatellite.values();
    }



    /**
     * cleans the previously stored data
     */
    public void clearData() {
        interceptedMessageBySatellite = new HashMap<>();
    }


}
