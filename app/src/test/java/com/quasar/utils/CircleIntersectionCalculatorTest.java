package com.quasar.utils;

import com.quasar.model.Circle;
import com.quasar.model.Point;
import com.quasar.utils.CircleIntersectionCalculator;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class CircleIntersectionCalculatorTest {

    @Inject
    private CircleIntersectionCalculator circleIntersectionCalculator;

    @Test
    void testIntersectionC1InsideC2() {
        // Los circulos no se intersectan
        // c1 esta contenido dentro de c2
        Circle c1 = new Circle(0,0, 10);
        Circle c2 = new Circle(0,0, 20);

        List<Point> points = circleIntersectionCalculator.getIntersectionPoints(c1, c2);
        // no debe haber puntos en la lista
        assertEquals(0, points.size());
    }

    @Test
    void testIntersectionCirclesDoesNotCollide() {
        // Los circulos no se intersectan
        Circle c1 = new Circle(10,10, 1);
        Circle c2 = new Circle(5,5, 1);

        List<Point> points = circleIntersectionCalculator.getIntersectionPoints(c1, c2);
        // no debe haber puntos en la lista
        assertEquals(0, points.size());
    }


    @Test
    void testIntersectionCircles() {
        // Los circulos no se intersectan
        Circle c1 = new Circle(10,10, 10);
        Circle c2 = new Circle(11,0, 1);

        List<Point> points = circleIntersectionCalculator.getIntersectionPoints(c1, c2);
        // no debe haber puntos en la lista
        assertEquals(2, points.size());

        Point p0 = points.get(0);
        Point p1 = points.get(1);
        assertEquals(11.98d, p0.getX());
        assertEquals(0.20d, p0.getY());

        assertEquals(10d, p1.getX());
        assertEquals(0d, p1.getY());
    }


    @Test
    void testIntersectionCircles00() {
        // Los circulos no se intersectan
        Circle c1 = new Circle(0,0, 10);
        Circle c2 = new Circle(11,0, 1);

        List<Point> points = circleIntersectionCalculator.getIntersectionPoints(c1, c2);
        // no debe haber puntos en la lista
        assertEquals(2, points.size());

        Point p0 = points.get(0);
        Point p1 = points.get(1);
        assertEquals(10d, p1.getX());
        assertEquals(0d, p1.getY());

        // hay solo 1 punto de interseccion entre estos 2 circulos
        assertEquals(p0, p1);

    }

}
