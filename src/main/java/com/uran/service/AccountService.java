package com.uran.service;

import com.uran.domain.Account;

public interface AccountService {
    
    Account get(long userId);
    
    Account debitUserAccount(long userId, double value);
    
    Account creditUserAccount(long userId, double value);
    
    Account debitStationAccount(double value);
    
    Account creditStationAccount(double value);
    
    void transferToStation(long userId, double value);
    
    void transferToUser(long userId, double value);
    
    Account save(Account account);
    
    Account update(Account account);
}
