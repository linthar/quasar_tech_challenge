package com.quasar.rest.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Introspected
public class TopSecretSplitRequest {
    private double distance;
    private String[] message;
}
