package com.uran.service;

import com.uran.domain.Horse;
import com.uran.dto.HorseDto;

import java.util.List;

public interface HorseService {
    
    //Page<Horse> findAll(Pageable pageable);
    List<Horse> findAll();
    Horse findById(long id);
    void delete(long id);
    Horse save(Horse horse);
    List<Horse> save(List<Horse> horse);
    Horse update(Horse horse);
    Horse update(HorseDto horseDto);
    //List<Horse> findByReadyTrue();
    void addWins(long horseId);
}
