package com.quasar.model;

import lombok.*;

/**
 * Representa un circulo en la cuadricula
 * (utilizado para triangular la posicion del emisor del mensaje)
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Circle {
    private Point center;
    private double radius;

    public Circle(double x, double y, double radio) {
        this.center = new Point(x, y);
        this.radius = radio;
    }
    public double getCenterX(){
        return center.getX();
    }
    public double getCenterY(){
        return center.getY();
    }


}
