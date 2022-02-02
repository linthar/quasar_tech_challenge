package com.quasar.rest.dto;

import com.quasar.model.InterceptedMessage;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Introspected
public class TopSecretRequest {

    private List<InterceptedMessage> satellites;
}
