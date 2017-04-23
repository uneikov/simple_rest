package com.uran.repository;

import com.uran.domain.Horse;
import com.uran.domain.projections.HorseNameView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(excerptProjection = HorseNameView.class)
public interface HorseRepository extends JpaRepository<Horse, Long> {
    
    @RestResource(path = "/names", rel = "names")
    List<Horse> findByReadyTrue();
    
    Horse findById(@Param("horseId") final long horseId);
    
    @Override
    List<Horse> findAll();
    
    Horse findByName(@Param("name") String name);
    //Horse findByRuName(@Param("name") String name);
}
