package com.example.model.generator;

import com.example.model.Order;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class OrderGenerator {

    /**
     * create random order
     * @param colors massive of colors (BLACK, GREY or BLACK and GREY)
     * @return order
     */
    public static Order create(String[] colors) {
        Random random = new Random();
        String randomString = RandomStringUtils.random(10, true, false);
        String randomDate = RandomStringUtils.random(4, false, true);
        Number randomNumber = random.nextInt(5) + 1;
        return new Order(
                randomString,
                randomString,
                randomString,
                randomString,
                randomString,
                randomNumber,
                randomDate,
                colors,
                randomString
        );
    }
}
