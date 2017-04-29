package com.uran.repository;

import com.uran.domain.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

//@RepositoryRestResource(path = "races", collectionResourceRel = "races_list", itemResourceRel = "race")
//@EnableTransactionManagement
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RepositoryRestResource
public interface RaceRepository extends JpaRepository<Race, Long> {
    
    @Override
    @RestResource(exported = false)
    @SuppressWarnings("unchecked")
    Race save(Race race);
    
    @Override
    @RestResource(exported = false)
    void delete(Race race);
    
    @Override
    @RestResource(exported = false)
    void deleteAll();
    
    /*@RestResource(exported = false)
    void delete(long id);*/
}

  /*@Modifying
    @Transactional
    @Query("delete from Race r where r.id=?1")
    //@Secured("ADMIN")
    //@RestResource(path = "deleteByRaceId", exported = true)
    //@ResponseBody
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@Param("raceId") Long raceId);
    
    @Modifying
    @Transactional
    @Query("delete from Race r where r=?1")
    //@RestResource(path = "deleteByRaceBody", exported = false)
    void deleteByRaceBody(@RequestBody Race race);*/