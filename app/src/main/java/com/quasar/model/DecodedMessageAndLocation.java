package com.quasar.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa el mensaje decodificado y la ubicaion del emisor del mensaje
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Introspected
public class DecodedMessageAndLocation {
    private Point position;
    private String message;


}
