package com.quasar.utils;

import com.quasar.model.Point;
import com.quasar.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void scaleTo2Decimals() {
        assertEquals(1.23d, Utils.scaleTo2Decimals(1.23456789d));
        assertEquals(200.55d, Utils.scaleTo2Decimals(200.5499d));
        assertEquals(-1.23d, Utils.scaleTo2Decimals(-1.23456789d));
        assertEquals(-100.25d, Utils.scaleTo2Decimals(-100.24999d));
    }

    @Test
    void getCommonPoint() {
        Point pa11 = new Point(1,1);
        Point pa12 = new Point(1,2);
        Point pa21 = new Point(2,1);
        Point pa22 = new Point(2,2);

        Point pb11 = new Point(1,1);
        Point pb12 = new Point(1,2);
        Point pb21 = new Point(2,1);
        Point pb22 = new Point(2,2);

        Point pc11 = new Point(1,1);
        Point pc12 = new Point(1,2);
        Point pc21 = new Point(2,1);
        Point pc22 = new Point(2,2);


        // pa11 =  pb11 = pc11 but are not the "same object"
        // this method must take pa11 and pb11 as the "same point"

        assertEquals(pc11, Utils.getCommonPoint(Arrays.asList(pa11, pa12), Arrays.asList(pb11, pb22)));
        assertEquals(pc12, Utils.getCommonPoint(Arrays.asList(pa12, pa21), Arrays.asList(pb11, pb12)));
        assertEquals(pc22, Utils.getCommonPoint(Arrays.asList(pa21, pa22), Arrays.asList(pb11, pb22)));
        assertEquals(pc11, Utils.getCommonPoint(Arrays.asList(pa22, pa11), Arrays.asList(pb11, pb11)));
        assertNull(Utils.getCommonPoint(Arrays.asList(pa21, pa22), Arrays.asList(pb12, pb11)));
        assertNull(Utils.getCommonPoint(Arrays.asList(pa11, pa22), Arrays.asList(pb12, pb21)));
    }
}