package com.uran.util;

import com.uran.domain.Horse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.uran.SimpleRestApplication.NUMBER_OF_HORSES_FOR_RACE;

public class RandomUtil {

    public static List<Horse> getHorsesForRace(final List<Horse> horses) {
        return getShuffled(horses).subList(0, NUMBER_OF_HORSES_FOR_RACE);
    }

    public static Horse getRandomHorseFromList(final List<Horse> horses) {
        return getShuffled(horses).get(0);
    }

    public static List<Integer> getRandomTimePoints(final int start, final int end, final int count) {
        return ThreadLocalRandom.current()
                .ints(count, start, end)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
    }

    private static List<Horse> getShuffled(final List<Horse> horses) {
        final List<Horse> randomHorses = new ArrayList<>(horses);
        Collections.shuffle(randomHorses, new Random(System.nanoTime()));
        return randomHorses;
    }
    
    public static String getRandomCardNumber() {
        return new Random().ints(48,57)
                .filter(i -> (i < 58) || (i > 64 && i < 91) || (i > 96))
                .limit(16)
                .collect(StringBuilder::new, (sb, i) -> sb.append((char) i), StringBuilder::append).toString();
    }
}
