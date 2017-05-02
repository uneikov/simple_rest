package com.uran.repository;


import com.uran.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface UserRepository extends JpaRepository<User, Long> {
    
    //@Query("select u from User u where u.email = ?1")
    User findByEmailIgnoringCase(@Param("email") String email);
    
    //@Query("select u from User u where u.name = :name")
    User findByName(@Param("name") String name);
    
    /*@Override
    @Modifying
    @Query("delete from User u where u.id=?1")
    void delete(@Param("userId") Long userId);*/
    
}
