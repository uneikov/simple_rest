package com.uran.repository;


import com.uran.domain.Stake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RepositoryRestResource
public interface StakeRepository extends JpaRepository<Stake, Long> {
    
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Stake> findByUserId(@Param("userId") long userId, Pageable pageable);
    
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Stake> findByRaceId(@Param("raceId") long raceId, Pageable pageable);
   
    @Query("select s from Stake s where s.race.id=?1")
    List<Stake> getListByRaceId(@Param("raceId") long raceId);
    
    List<Stake> findByHorseIdAndRaceId(@Param("horseId") long horseId, @Param("raceId") long raceId);
    
    //Page<Stake> findByRaceIdAndUserId(@Param("raceId") long raceId, @Param("userId") long userId, Pageable pageable);
    
    List<Stake> findByDateTime(@Param("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime);
    
    List<Stake> findByDateTimeBefore(@Param("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime);
    
    //@Query("select s from Stake s where s.dateTime between :startDate and :endDate order by s.dateTime desc")
    List<Stake> findByDateTimeBetween(
            @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    );
    
    List<Stake> findByDateTimeBetweenAndWinsIs(
            @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Param("option") boolean option
    );
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Stake> findByEditableTrue(Pageable pageable);
    
   /* @Override
    void deleteInBatch(Iterable<Stake> iterable);*/
    
    List<Stake> findByRaceIdAndWinsTrue(@Param("raceId") final long raceId);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Stake s set s.editable=false where s.race.id=?1")
    void setUneditable(@Param("raceId") long raceId);
    
    @Transactional
    default Double getAllCashByRaceId(@Param("raceId") long raceId) {
        return getListByRaceId(raceId).stream().mapToDouble(Stake::getStakeValue).sum();
    }
    
}
