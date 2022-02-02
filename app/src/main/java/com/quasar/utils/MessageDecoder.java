package com.quasar.utils;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class MessageDecoder {

    /**
     * Calculates the last decoded message using the previously collected information
     *
     * @param lastDecodedMessage previously collected information
     * @param interceptedMessage new encoded message (new information to add)
     * @return the new decodedMessage resulting from use all the information gathered
     * (new decodedMessage could be still incomplete)
     */
    public String[] calculateNewDecodedMessage(String[] lastDecodedMessage,
                                               String[] interceptedMessage) {

        log.debug("trying to decode message. lastDecodedMessage: {} , interceptedMessage: {} ", lastDecodedMessage, interceptedMessage);

        int newDecodedMessageLength = Math.min(lastDecodedMessage.length, interceptedMessage.length);

        String[] newDecodedMessage = new String[newDecodedMessageLength];

        int lastDecodedMessageIdx = 0;
        int interceptedMessagesIdx = 0;

        // se asume que el desfasaje esta al principio del mensaje, nunca en el medio o al final
        if (lastDecodedMessage.length > interceptedMessage.length) {
            // lastDecodedMessage esta desfasado
            lastDecodedMessageIdx = lastDecodedMessage.length - interceptedMessage.length;
        } else if (lastDecodedMessage.length < interceptedMessage.length) {
            //interceptedMessage esta desfasado
            interceptedMessagesIdx = interceptedMessage.length - lastDecodedMessage.length;
        }

        // armamos el nuevo decoded message
        for (int i = 0; i < newDecodedMessageLength; i++) {
            String s1 = lastDecodedMessage[lastDecodedMessageIdx++];
            String s2 = interceptedMessage[interceptedMessagesIdx++];

            // seleccionamos las palabras no blank ("")
            // para armar el nuevo mensaje decodificado
            if (!s1.isBlank()) {
                newDecodedMessage[i] = s1;
            } else {
                newDecodedMessage[i] = s2;
            }
        }

        log.debug("decoding message. lastDecodedMessage: {}, interceptedMessage: {}, newDecodedMessage: {} ", lastDecodedMessage, interceptedMessage, newDecodedMessage);

        return newDecodedMessage;
    }


}
