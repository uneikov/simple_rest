package com.uran.repository;

import com.uran.domain.Account;
import com.uran.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;


@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByUserId(@Param("userId") long userId);
    Account findByUserEmailIgnoringCase(@Param("email") String email);
    Account findByUserRoles(Set<Role> roles);
}
