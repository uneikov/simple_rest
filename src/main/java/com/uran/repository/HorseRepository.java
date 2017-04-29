package com.uran.repository;

import com.uran.domain.Horse;
import com.uran.domain.projections.HorseNameView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(excerptProjection = HorseNameView.class)
public interface HorseRepository extends JpaRepository<Horse, Long>{
    
    @RestResource(path = "/names", rel = "names")
    List<Horse> findByReadyTrue();
    
    Horse findById(@Param("horseId") final long horseId);
    
    @Override
    List<Horse> findAll();
    
    Horse findByName(@Param("name") String name);
    //Horse findByRuName(@Param("name") String name);

    Long countAllByReadyTrue();

    @Query(value = "select h from Horse h")
    List<Horse> listHorses();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Horse h set h.age=:age where h.id=:horseId")
    void setAge(@Param("horseId") final long horseId, @Param("age") final int age);
}
