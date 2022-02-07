package com.quasar.persistence;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Getter
@Slf4j
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
        log.debug("clearing Satellites data");
        decodedMessage = null;
        messageWasDecoded = false;
    }

}
