package com.quasar.rest.dto;

import com.quasar.model.Point;
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
public class QuasarResponse {
    private Point position;
    private  String message;


}
