package com.uran.service;

import com.uran.domain.Horse;
import com.uran.repository.HorseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component(value = "horseService")
public class HorseServiceImpl implements HorseService {

    private final HorseRepository repository;

    @Autowired
    public HorseServiceImpl(HorseRepository repository) {
        this.repository = repository;
    }
    
    /*@Override
    public Page<Horse> findAll(final Pageable pageable) {
        return this.repository.findAll(pageable);
    }*/
    
    @Override
    public List<Horse> findAll() {
        return this.repository.findAll();
    }
    
    @Override
    public Horse findById(final long id) {
        return this.repository.findOne(id);
    }
    
    @Override
    public Horse findByName(String name) {
        return this.repository.findByName(name);
    }
    
    @Override
    public void delete(long id) {
        this.repository.delete(id);
    }
    
    @Override
    public Horse save(Horse horse) {
        return this.repository.save(horse);
    }
    
    @Override
    public List<Horse> save(List<Horse> horses) {
        return this.repository.save(horses);
    }
    
    @Override
    public Horse update(Horse horse) {
        return this.repository.save(horse);
    }
    
    //@Override
    /*@Transactional
    public Horse update(HorseDto horseDto) {
        Horse horse = updateFromTo(this.repository.findOne(horseDto.getId()), horseDto);
        return this.repository.save(horse);
    }*/
    
    /*@Override
    public List<Horse> findByReadyTrue() {
        return this.repository.findByReadyTrue();
    }*/
    
    @Override
    @Transactional
    public void addWins(long horseId) {
        Horse horse = this.repository.findById(horseId);
        horse.setWins(horse.getWins() + 1);
        this.repository.save(horse);
    }
}
