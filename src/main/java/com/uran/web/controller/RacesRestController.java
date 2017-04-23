package com.uran.web.controller;

import com.uran.domain.Race;
import com.uran.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api/races")
public class RacesRestController {
    @Autowired
    private RaceRepository repository;
    
    @RequestMapping(value = "/delete", consumes = APPLICATION_JSON_UTF8_VALUE)
    public void delete(@Valid @RequestBody final Race race ) {
        final Race one = this.repository.findOne(Example.of(race));
        this.repository.delete(one);
    }
}
