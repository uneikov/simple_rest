package com.uran.service;

import com.uran.domain.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RaceService {
    Page<Race> findAll(Pageable pageable);
    List<Race> findAll();
    Race findById(long id);
    Race save(Race race);
    Race update(Race race);
}
