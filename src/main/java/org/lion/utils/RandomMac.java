package org.lion.utils;

import io.reactivex.Observable;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomMac {
    public static void main(String[] args) {

        String parse = new RandomMac().parse();

    }

    public String parse() {
        Random random = new Random();
        return Observable.range(0, 6)
                .map(integer -> random.nextInt())
                .map(this::normal)
                .toList()
                .blockingGet()
                .stream()
                .collect(Collectors.joining(":"));

    }

    private String normal(int digit) {
        int d = digit & 0xff;
        return String.format("%02X", d);
    }
}
