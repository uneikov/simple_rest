package com.uran.util.horse;

import com.uran.domain.Horse;
import com.uran.dto.HorseDto;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HorseUtil {
    
    public static Horse createNewFromTo(final HorseDto newHorse) {
        return new Horse(null, newHorse.getName(), newHorse.getRuName(), newHorse.getAge(), 0, false);
    }
    
    public static Horse updateFromTo(final Horse horse, final HorseDto horseDto) {
        horse.setName(horseDto.getName());
        horse.setRuName(horseDto.getRuName());
        horse.setAge(horseDto.getAge());
        return horse;
    }
    
    // return "en_name:ru_name,en_name:ru:name ..."
    public static String getSerialized(final List<Horse> horses) {
        return horses.stream()
                .sorted(Comparator.comparing(Horse::getName))
                .map(horse -> horse.getName() + ":" + horse.getRuName())
                .collect(Collectors.joining(","));
    }
    
    // return map(en_name->ru_name, ...)
    private static Map<String, String> getDeserialized(final String horses) {
        return Arrays.stream(horses.split(","))
                .sorted()
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
        
    }
    
}