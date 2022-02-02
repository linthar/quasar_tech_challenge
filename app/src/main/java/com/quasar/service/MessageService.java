package com.quasar.service;

import com.quasar.utils.MessageDecoder;
import com.quasar.utils.Utils;
import com.quasar.persistence.MessageRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
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
            return Utils.asString(messageRepository.getDecodedMessage());
        }
        // else
        return null;
    }


    public void storeDecodedMessage(String[] decodedMessage) {
        messageRepository.setDecodedMessage(decodedMessage);
        // try to detect if the message was decoded
        boolean isDecoded = true;
        for (String word : decodedMessage) {
            isDecoded = isDecoded && !word.isBlank();
        }
        messageRepository.setMessageWasDecoded(isDecoded);
    }


}


