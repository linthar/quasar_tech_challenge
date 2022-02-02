package com.quasar.utils;

import com.quasar.model.Circle;
import com.quasar.model.Point;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.quasar.utils.Utils.scaleTo2Decimals;
import static java.lang.Math.*;

@Singleton
@Slf4j
public class CircleIntersectionCalculator {

    public List<Point> getIntersectionPoints(Circle c1, Circle c2) {
        log.debug("calculating intersection points for {} & {}", c1, c2);
        /*
         * dx y dy son las distancias entre los centros de los circulos
         */
        double distanceX = c2.getCenterX() - c1.getCenterX();
        double distanceY = c2.getCenterY() - c1.getCenterY();

        /*
         * distance = distancia en linea recta entre los dos centros
         */
        double distanceBetweenCenters = hypot(distanceX, distanceY);

        // check: si los circulos no se intersectan
        // no hay puntos de interseccion
        if (!circlesCollide(distanceBetweenCenters, c1, c2)) {
            log.debug("circles doesn't collide {} & {}", c1, c2);
            // los circulos no se intersectan
            return new ArrayList<>();
        }


        /*
         * 'punto 0' = interseccion entre la linea que conecta los 2 centros
         * con la linea que conecta las 2 intersecciones
         */

        double r1Sqr = pow(c1.getRadius(), 2);
        double r2Sqr = pow(c2.getRadius(), 2);
        double distanceSqr = pow(distanceBetweenCenters, 2);

        // a = distancia del centro de c1 a p0
        double a = (r1Sqr - r2Sqr + distanceSqr) / (2.0 * distanceBetweenCenters);

        // Determar el p0  = (x0, y0)
        double x0 = c1.getCenterX() + (distanceX * a / distanceBetweenCenters);
        double y0 = c1.getCenterY() + (distanceY * a / distanceBetweenCenters);

        // h = Distancia desde p0 a los puntos de interseccion
        double h = sqrt(r1Sqr - (a * a));

        // offset entre p0 y los puntos de interseccion
        double rx = -distanceY * (h / distanceBetweenCenters);
        double ry = distanceX * (h / distanceBetweenCenters);

        // Determinar los 2 puntos de interseccion
        Point intersection0 = new Point(scaleTo2Decimals(x0 + rx), scaleTo2Decimals(y0 + ry));
        Point intersection1 = new Point(scaleTo2Decimals(x0 - rx), scaleTo2Decimals(y0 - ry));

        List<Point> result = Arrays.asList(intersection0, intersection1);
        log.debug("intersection points for {} & {}. result: {}", c1, c2, result);
        return result;
    }

    private boolean circlesCollide(double distanceBetweenCenters, Circle c1, Circle c2) {
        if (distanceBetweenCenters > (c1.getRadius() + c2.getRadius())) {
            // circles do not intersect.
            return false;
        }
        if (distanceBetweenCenters < abs(c1.getRadius() - c2.getRadius())) {
            //  one circle is contained in the other
            return false;
        }
        return true;
    }


}
