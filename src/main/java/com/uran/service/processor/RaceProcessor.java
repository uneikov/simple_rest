package com.uran.service.processor;

public interface RaceProcessor {

    void forbidStakeEditing(long raceId);

    void process(long horseId, long raceId);

}
