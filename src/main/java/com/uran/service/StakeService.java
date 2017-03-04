package com.uran.service;

import com.uran.domain.Stake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface StakeService {
    
    Page<Stake> findAll(Pageable pageable);
    
    List<Stake> findAll();
    
    Stake findById(long id);
    
    List<Stake> findByRaceId(long id);
    
    Page<Stake> findAllByUserId(long id, Pageable pageable);
    
    //Page<Stake> findAllByRaceIdAndUserId(final long raceId, final long userId, final Pageable pageable);
        
    Stake addStake(Stake stake);
    
    void delete(long id);
    
    Stake save(Stake stake);
    
    //Stake save(Stake stake, long userId);
    
    //Stake update(Stake stake, long userId);
    
    Stake update(Stake stake);
    
    Double getAllCash(long raceId);
    
    void setWinningStakes(long horseId, long raceId);
    
    List<Stake> getWinningStakes(long raceId);
    
    List<Stake> processWinningStakes(List<Stake> winningStakes, Map<Long, Double> winningMap);
    
    void setNotEditable(long raceId);
    
    void setUneditable(long raceId);
}
