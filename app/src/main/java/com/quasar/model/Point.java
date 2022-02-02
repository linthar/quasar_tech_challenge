package com.quasar.model;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

/**
 * Representa un punto en la cuadricula
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Introspected
public class Point {
    private double x, y;
}
