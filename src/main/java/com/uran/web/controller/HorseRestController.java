package com.uran.web.controller;

import com.uran.domain.Horse;
import com.uran.dto.HorseDto;
import com.uran.service.HorseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api/horses")
public class HorseRestController {

    private HorseService horseService;

    private ModelMapper modelMapper;

    @Autowired
    public HorseRestController(HorseService horseService, ModelMapper modelMapper) {
        this.horseService = horseService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public HorseDto addHorse(@Valid @RequestBody final HorseDto horseDto) {
        final Horse horse = convertToEntity(horseDto);
        final Horse horseAdded = horseService.save(horse);
        return convertToDto(horseAdded);
    }

    private HorseDto convertToDto(Horse horse) {
        return modelMapper.map(horse, HorseDto.class);
    }

    private Horse convertToEntity(HorseDto horseDto) {
        return modelMapper.map(horseDto, Horse.class);
    }
}

