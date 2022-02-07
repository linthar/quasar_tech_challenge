package com.quasar.service;

import com.quasar.persistence.MessageRepository;
import com.quasar.utils.MessageDecoder;
import com.quasar.utils.Utils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Singleton
@Slf4j
public class MessageService {

    @Inject
    private MessageRepository messageRepository;
    @Inject
    private MessageDecoder messageDecoder;

    /**
     * cleans the previously stored data
     */
    public void clearData() {
        messageRepository.clearData();
    }


    /**
     * Tries to decode the message with the info collected
     *
     * @param interceptedMessages
     * @return the decoded message or null if the message won't be decoded with the current
     * collected information
     */
    public String attemptMessageDecode(String[] interceptedMessages) {
        log.debug("attemptMessageDecode: {}", Utils.getDebugString(interceptedMessages));
        if (messageRepository.isMessageWasDecoded()) {
            log.debug("message was already decoded");
            return getDecodedMessageOrNull();
        }

        String[] newDecodedMessage;
        if (messageRepository.getDecodedMessage() == null) {
            newDecodedMessage = interceptedMessages;
        } else {
            newDecodedMessage = messageDecoder.calculateNewDecodedMessage(messageRepository.getDecodedMessage(), interceptedMessages);
        }

        this.storeDecodedMessage(newDecodedMessage);
        return getDecodedMessageOrNull();
    }

    /**
     * Answer the decoded message
     *
     * @return the decoded message as String or null if message couldn't be decoded
     */
    private String getDecodedMessageOrNull() {
        if (messageRepository.isMessageWasDecoded()) {
            String msg = Utils.asString(messageRepository.getDecodedMessage());
            log.debug("Answering DecodedMessage: {}", msg);
            return msg;
        }
        // else
        log.debug("message wasn't decoded. Answering NULL value");
        return null;
    }


    public void storeDecodedMessage(String[] decodedMessage) {

        log.debug("storing (last version of) DecodedMessage: {}}", Utils.getDebugString(decodedMessage));
        messageRepository.setDecodedMessage(decodedMessage);
        // try to detect if the message was decoded
        boolean isDecoded = true;
        for (String word : decodedMessage) {
            isDecoded = isDecoded && !word.isBlank();
        }

        if (isDecoded) {
            log.debug(" Message was Decoded!!: {}}", Utils.getDebugString(decodedMessage));
        }
        messageRepository.setMessageWasDecoded(isDecoded);
    }


}


