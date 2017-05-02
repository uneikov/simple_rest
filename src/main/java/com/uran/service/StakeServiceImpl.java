package com.uran.service;

import com.uran.domain.Stake;
import com.uran.repository.StakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "stakeService")
public class StakeServiceImpl implements StakeService{
    
    private final StakeRepository repository;
    private final AccountService accountService;

    @Autowired
    public StakeServiceImpl(StakeRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public Page<Stake> findAll(Pageable pageable){
        return this.repository.findAll(pageable);
    }
    
    @Override
    public List<Stake> findAll() {
        return this.repository.findAll();
    }
    
    @Override
    public Stake findById(final long id) {
        return this.repository.findOne(id);
    }
    
    @Override
    public List<Stake> findByRaceId(final long id) {
        return this.repository.getListByRaceId(id);
    }
    
    @Override
    public Page<Stake> findAllByUserId(final long id, final Pageable pageable) {
        return this.repository.findByUserId(id, pageable);
    }
    
    @Override
    public Page<Stake> findAllByRaceIdAndUserId(final long raceId, final long userId,  final Pageable pageable) {
        return this.repository.findByRaceIdAndUserId(raceId, userId, pageable);
    }

    @Override
    public void delete(final long id) {
        repository.delete(id);
    }
    
    @Override
    @Transactional
    public Stake save(final Stake stake) {
        Assert.notNull(stake, "stake must not be null");
        Stake saved = this.repository.save(stake);
        accountService.transferToStation(saved.getUser().getId(), saved.getStakeValue());
        return saved;
    }
    
   /* @Override
    @Transactional
    public Stake save(final Stake stake, final long userId) {
        Assert.notNull(stake, "stake must not be null");
        stake.setUser(userService.findOne(userId));
        Stake created = repository.save(stake);
        accountService.transferToStation(userId, stake.getStakeValue());
        return created;
    }*/
    
    @Override
    @Transactional
    public Stake update(final Stake stake) {
        Assert.notNull(stake, "stake must not be null");
        final Stake oldStake = this.repository.findOne(stake.getId());
        final Stake newStake = this.repository.save(stake);
        //Stake updated = checkNotFoundWithId(repository.save(stake, userId), stake.getId());
        updateUserAndStationAccounts(oldStake.getStakeValue() - newStake.getStakeValue(), stake.getUser().getId());
        return newStake;
    }
    
    /*@Override
    public Stake update(Stake stake) {
        Assert.notNull(stake, "stake must not be null");
        return this.repository.save(stake);
    }*/
    //---------------------------------------------------------------------------------------
    
    @Override
    @Transactional
    public void setWinningStakes(final long horseId, final long raceId) {
        this.repository.save(
                this.repository.findByHorseIdAndRaceId(horseId, raceId).stream()
                .peek(stake -> stake.setWins(true)).collect(Collectors.toList())
        );
    }
    
    @Override
    public List<Stake> getWinningStakes(final long raceId) {
        return this.repository.findByRaceIdAndWinsTrue(raceId);
    }
    
    @Override
    @Transactional
    public List<Stake> processWinningStakes(List<Stake> winningStakes, Map<Long, Double> winningMap) {
        return this.repository.save(winningStakes.stream()
                .peek(stake -> stake.setAmount(winningMap.get(stake.getUser().getId())))
                .collect(Collectors.toList()));
    }
    
    @Override
    public Double getAllCash(final long raceId) {
        return this.repository.getListByRaceId(raceId).stream()
                .mapToDouble(Stake::getStakeValue)
                .sum();
    }
    
    @Override
    @Transactional
    public Long setNotEditable(final long raceId) {
        return (long) this.repository.save(
                this.repository.getListByRaceId(raceId).stream()
                        .peek(stake -> stake.setEditable(false))
                        .collect(Collectors.toList())).size();
    }
    
    @Override
    @Transactional
    public void setUneditable(final long raceId) {
        this.repository.setEditableByRaceId(raceId);
        /*this.repository.save(
                this.repository.getListByRaceId(raceId).stream()
                        .peek(stake -> stake.setEditable(false))
                        .collect(Collectors.toList())
        );*/
    }
    
    private void updateUserAndStationAccounts(final Double difValue, final long userId){
        if (difValue < 0) {
            accountService.transferToStation(userId, Math.abs(difValue));
        }else {
            accountService.transferToUser(userId, difValue);
        }
    }
}
