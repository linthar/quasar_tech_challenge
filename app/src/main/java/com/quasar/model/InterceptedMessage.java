package com.quasar.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa un mensaje (codificado/incompleto) interceptado por un satelite
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Introspected
public class InterceptedMessage {
    private String name;
    private double distance;
    private String[] message;
}
