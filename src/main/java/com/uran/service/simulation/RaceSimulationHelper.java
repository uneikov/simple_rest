package com.uran.service.simulation;


import com.uran.domain.Horse;

import java.util.List;

public interface RaceSimulationHelper {

    void createBots(int max);

    void initBots(int min, int max);

    void clearAccounts();

    void fillAccounts();

    void killBots();

    void startGamble();

    List<Horse> getHorsesForRace();

}
