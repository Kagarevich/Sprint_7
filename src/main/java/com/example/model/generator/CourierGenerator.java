package com.example.model.generator;

import com.example.model.Courier;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    /**
     * create random courier
     * @return random courier
     */
    public static Courier create() {
        String randomString = RandomStringUtils.random(10, true, false);
        return new Courier(
                randomString,
                randomString,
                randomString
        );
    }
}
