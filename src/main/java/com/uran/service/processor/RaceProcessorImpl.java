package com.uran.service.processor;


import com.uran.domain.Stake;
import com.uran.service.AccountService;
import com.uran.service.HorseService;
import com.uran.service.StakeService;
import com.uran.util.stake.StakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RaceProcessorImpl implements RaceProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(RaceProcessor.class);

    private final StakeService stakeService;
    private final AccountService accountService;
    private final HorseService horseService;

    public RaceProcessorImpl(StakeService stakeService, AccountService accountService, HorseService horseService) {
        this.stakeService = stakeService;
        this.accountService = accountService;
        this.horseService = horseService;
    }

    @Override
    @Transactional
    public void process(final long horseId, final long raceId) {
        horseService.addWins(horseId);
        stakeService.setWinningStakes(horseId, raceId);
        final Double allCash = stakeService.getAllCash(raceId);
        List<Stake> winningStakes = stakeService.getWinningStakes(raceId);
        if (!winningStakes.isEmpty()) {
            final Double winCash = StakeUtil.getValuesSum(winningStakes);
            final Double winRatio = allCash / winCash;
            final Map<Long, Double> winningMap = winningStakes.stream()
                    .collect(Collectors.toMap(s -> s.getUser().getId(), s -> s.getStakeValue() * winRatio));
            stakeService
                    .processWinningStakes(winningStakes, winningMap)
                    .forEach(stake -> accountService.transferToUser(stake.getUser().getId(), stake.getAmount()));
            LOG.info("Winning stakes count: {}, all cash from race stakes: {}", winningStakes.size(), allCash);
        } else {
            LOG.info("Nobody wins, station revenue: {}", allCash);
        }
    }

    @Override
    public void forbidStakeEditing(final long raceId) {
        stakeService.setNotEditable(raceId);
    }
}
