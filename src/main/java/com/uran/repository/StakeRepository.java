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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface StakeRepository extends JpaRepository<Stake, Long> {
    
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Stake> findByUserId(@Param("userId") final long userId, final Pageable pageable);
    
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Stake> findByRaceId(@Param("raceId") final long raceId, final Pageable pageable);
   
    //@Query("select s from Stake s where s.race.id=?1")
    List<Stake> getListByRaceId(@Param("raceId") final long raceId);
    
    List<Stake> findByHorseIdAndRaceId(@Param("horseId") final long horseId, @Param("raceId") final long raceId);
    
    //Page<Stake> findByRaceIdAndUserId(@Param("raceId") long raceId, @Param("userId") long userId, Pageable pageable);
    
    List<Stake> findByDateTime(@Param("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateTime);
    
    List<Stake> findByDateTimeBefore(@Param("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dateTime);
    
    //@Query("select s from Stake s where s.dateTime between :startDate and :endDate order by s.dateTime desc")
    List<Stake> findByDateTimeBetween(
            @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime endDate
    );
    
    List<Stake> findByDateTimeBetweenAndWinsIs(
            @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime endDate,
            @Param("option") boolean option
    );
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Stake> findByEditableTrue(final Pageable pageable);
    
   /* @Override
    void deleteInBatch(Iterable<Stake> iterable);*/
    
    List<Stake> findByRaceIdAndWinsTrue(@Param("raceId") final long raceId);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Stake s set s.editable=false where s.race.id=?1")
    void setEditableByRaceId(@Param("raceId") long raceId);
    
}
