package com.quasar.utils;

import com.quasar.model.Point;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Utils {

    /**
     * Scales the number to 2 decimal digits
     * e.g.: scaleTo2Decimals(1.23000456789d) will return 1.23d
     *
     * @param d number to scale
     * @return d trimmed to 2 decimals
     */
    public static double scaleTo2Decimals(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    /**
     * Concatenates all words in array into a single String
     *
     * @param strArray words array to generate the message
     * @return all words in strArray concatenated in a single String
     */
    public static String asString(String[] strArray){
        return Arrays.asList(strArray).stream() .filter(s -> !s.isBlank())
                .collect(Collectors.joining(" ", "", ""));
    }



    /**
     * finds the point that belongs to both lists
     *
     * @param l1 points list to compare
     * @param l2 points list to compare
     * @return the point that is present in both lists
     */
    public static Point getCommonPoint(List<Point> l1, List<Point> l2) {

        List<Point> common = l1.stream().filter(l2::contains).collect(toList());

        if (common.size() > 0) {
            return common.get(0);
        }
        return null;

    }

}
