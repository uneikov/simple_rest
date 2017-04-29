package com.uran;

import com.uran.domain.*;

import java.time.LocalDateTime;

public class TestData {
    public static Horse HORSE = new Horse("Red Ghost", "Красный призрак",3, 0);

    public static Race RACE = new Race(
            5L,
            LocalDateTime.of(2016, 8,5, 10,0,0),
            LocalDateTime.of(2016, 8,5, 10,45,0)
    );

    private static Account ACCOUNT = new Account("777788889999000",0.0, new User());

    public static User USER = new User(
            null,
            "Peter",
            "peter.u@gmail.com",
            "pass",
            ACCOUNT,
            Role.ROLE_USER
    );

}
