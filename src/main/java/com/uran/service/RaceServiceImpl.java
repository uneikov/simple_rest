package com.uran.service;

import com.uran.domain.Race;
import com.uran.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component("raceService")
public class RaceServiceImpl implements RaceService{
    
    private final RaceRepository repository;

    @Autowired
    public RaceServiceImpl(RaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Race> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
    
    @Override
    public List<Race> findAll() {
        return this.repository.findAll();
    }
    
    @Override
    public Race findById(long id) {
        return this.repository.findOne(id);
    }
    
    @Override
    public Race save(Race race) {
        Assert.notNull(race , "race must not be null");
        return this.repository.save(race);
    }
    
    @Override
    public Race update(Race race) {
        Assert.notNull(race , "race must not be null");
        return this.repository.save(race);
    }
}
