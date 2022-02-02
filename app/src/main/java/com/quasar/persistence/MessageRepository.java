package com.quasar.persistence;


import com.quasar.model.InterceptedMessage;
import com.quasar.model.Point;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
@Getter
public class MessageRepository {

    // se guardan los mensajes interceptados en un "storage en memoria"
    // (solo para este Tech challenge)

    @Setter
    private String[] decodedMessage = null;
    @Setter
    private boolean messageWasDecoded = false;

    /**
     * cleans the previously stored data
     */
    public void clearData() {
        decodedMessage = null;
        messageWasDecoded = false;
    }

}
