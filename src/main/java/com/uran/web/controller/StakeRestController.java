package com.uran.web.controller;

import com.uran.AuthorizedUser;
import com.uran.domain.Horse;
import com.uran.domain.Race;
import com.uran.domain.Stake;
import com.uran.domain.User;
import com.uran.dto.StakeDto;
import com.uran.service.HorseService;
import com.uran.service.StakeService;
import com.uran.service.UserService;
import com.uran.service.scheduler.RaceScheduler;
import com.uran.util.horse.HorseUtil;
import com.uran.web.util.RestPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api/stakes")
public class StakeRestController {

    private StakeService stakeService;

    private HorseService horseService;

    private UserService userService;

    @Autowired
    public StakeRestController(StakeService stakeService, HorseService horseService, UserService userService) {
        this.stakeService = stakeService;
        this.horseService = horseService;
        this.userService = userService;
    }

    @GetMapping(value = "/setUneditable/{raceId}")
    @ResponseStatus(HttpStatus.OK)
    public void setUneditable(@PathVariable("raceId") final long raceId) {
        stakeService.setUneditable(raceId);
    }

    @GetMapping(value = "/cash/{raceId}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Double getAllCash(@PathVariable("raceId") final long raceId) {
        return stakeService.getAllCash(raceId);
    }

    @PostMapping(value = "/add", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public StakeDto addStake(@Valid @RequestBody final StakeDto stakeDto) {
        final Stake stake = convertToEntity(stakeDto);
        final Stake stakeAdded = stakeService.save(stake);
        return convertToDto(stakeAdded);
    }

    private StakeDto convertToDto(Stake stake) {
        return new StakeDto(
                stake.getStakeValue(),
                stake.getHorse().getName() + ":" + stake.getHorse().getRuName()
        );
    }

    private Stake convertToEntity(StakeDto stakeDto) {
        final Map.Entry<String, String> fullName =
                HorseUtil.getDeserialized(stakeDto.getFullHorseName()).entrySet().iterator().next();
        final Horse horse = horseService.findByName(fullName.getKey());
        RestPreconditions.checkFound(horse, "This horse does not exist, check horse name");
        final User user = userService.findOne(AuthorizedUser.id());
        final Race race = RaceScheduler.getCurrentRace();
        return new Stake(null, user, horse, race, stakeDto.getStakeValue());
    }

}