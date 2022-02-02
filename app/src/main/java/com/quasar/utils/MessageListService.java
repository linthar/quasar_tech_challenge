package com.quasar.utils;

import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MessageListService {

    public String getMessage(List<String>... interceptedMessages) {

        List<String> decodedMessage = new ArrayList<>();
        for (List<String> interceptedMsg : interceptedMessages) {
            decode(decodedMessage, new ArrayList(interceptedMsg));
        }
        return asString(decodedMessage);
    }

    private String asString(List<String> strList){
        return strList.stream().filter(s -> !s.isBlank())
                .collect(Collectors.joining(" ", "", ""));
    }


    private void decode(List<String> decodedMessage, List<String> interceptedMsg) {

        if (decodedMessage.isEmpty()) {
            decodedMessage.addAll(interceptedMsg);
            // no hay datos previos, el msg actual es la unica info que tengo
            return;
        }

        // else
        if (decodedMessage.size() != interceptedMsg.size()) {
            // ver (y ajustar) donde esta el desfasaje
            ajustarDesfasaje(decodedMessage, interceptedMsg);
        }

        //  decodedMessage.size() == interceptedMsg.size()
        for (int i = 0; i < decodedMessage.size(); i++) {
            String decodedString = decodedMessage.get(i);

            if (decodedString.isBlank()) {
                String interceptedString = interceptedMsg.get(i);
                decodedMessage.set(i, interceptedString);
            }

        }
    }

    /**
     * busca y ajusta el desfasaje entre los mensajes
     *
     * @param msg1
     * @param msg2
     */
    private void ajustarDesfasaje(List<String> msg1, List<String> msg2) {
        List<String> maxMsg = msg1;
        List<String> minMsg = msg2;
        if (maxMsg.size() < minMsg.size()) {
            maxMsg = msg2;
            minMsg = msg1;
        }

        for (int maxIdx = 0; maxIdx < maxMsg.size(); maxIdx++) {
            String maxStr = maxMsg.get(maxIdx);
            if (!maxStr.isBlank()) {
                int minIdx = minMsg.indexOf(maxStr);
                if (minIdx != -1 && minIdx != maxIdx) {
                    addBlanks(minMsg, minIdx, maxIdx - minIdx);
                }
            }
        }

        /// si el desfasaje esta al final
        addBlanks(minMsg, minMsg.size()-1,  maxMsg.size()-minMsg.size());
    }

    /**
     * Adds N times a blank string to the list in the given index
     * (will shift the fromIdx elements to the right)
     *
     * @param msgToAdjust String list to add the blanks
     * @param fromIdx starting index to add the blanks
     * @param quantity number of blanks to be added
     */
    private void addBlanks(List<String> msgToAdjust, int fromIdx, int quantity) {
        List<String> newList = new ArrayList<>();

        // se agregan los elementos anterioes al index de inicio
        for (int i = 0; i < fromIdx; i++) {
            newList.add(msgToAdjust.get(i));
        }

        for (int i = 0; i < quantity; i++) {
            newList.add("");
        }

        // se agregan los elementos restantes
        for (int i = fromIdx; i < msgToAdjust.size(); i++) {
            newList.add(msgToAdjust.get(i));
        }

        msgToAdjust.clear();
        msgToAdjust.addAll(newList);
    }
}


