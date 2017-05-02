package com.uran.service;

import com.uran.domain.Account;
import com.uran.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component(value = "accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
    
    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Account get(long userId) {
        return this.repository.findOne(userId);
    }
    
    @Override
    public Account debitUserAccount(final long userId, final double value) {
       //if (value < 0.0) throw new NegativeValueException();
       final Account account = this.repository.findByUserId(userId);
       account.setBalance(account.getBalance() - value);
       return this.repository.save(account);
    }
    
    @Override
    public Account creditUserAccount(final long userId, final double value) {
        final Account account = this.repository.findByUserId(userId);
        account.setBalance(account.getBalance() + value);
        return this.repository.save(account);
    }
    
    @Override
    public Account debitStationAccount(final double value) {
        final Account account = this.repository.findByUserId(getStationId());
        account.setBalance(account.getBalance() - value);
        return this.repository.save(account);
    }
    
    @Override
    public Account creditStationAccount(final double value) {
        final Account account = this.repository.findByUserId(getStationId());
        account.setBalance(account.getBalance() + value);
        return this.repository.save(account);
    }
    
    @Override
    public void transferToUser(long userId, double value) {
        debitStationAccount(value);
        creditUserAccount(userId, value);
    }
    
    @Override
    public void transferToStation(final long userId, final double value) {
        debitUserAccount(userId, value);
        creditStationAccount(value);
    }
    
    @Override
    public Account save(Account account) {
        Assert.notNull(account, "account must not be null");
        return this.repository.save(account);
    }
    
    @Override
    public Account update(Account account) {
        Assert.notNull(account, "account must not be null");
        return this.repository.save(account);
    }
    
    private Long getStationId() {
        return this.repository.findByUserEmailIgnoringCase("station@gamblingstation.com").getUser().getId();
        /*Set<Role> roles = new HashSet<Role>();
        roles.add(Role.ROLE_STATION);
        return this.repository.findByUserRoles(roles).getUser().getId();*/
    }
}
