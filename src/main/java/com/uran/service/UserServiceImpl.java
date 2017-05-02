package com.uran.service;

import com.uran.AuthorizedUser;
import com.uran.domain.User;
import com.uran.dto.UserDto;
import com.uran.repository.AccountRepository;
import com.uran.repository.UserRepository;
import com.uran.util.user.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.uran.util.user.UserUtil.prepareToSave;
import static com.uran.util.user.UserUtil.updateFromTo;

@Service("userDetailsService")
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    
    @Override
    public User findOne(long id) {
        return this.userRepository.findOne(id);
    }
    
    @Override
    @Transactional
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(user.getAccount(), "account must not be null");
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public User save(UserDto userDto) {
        Assert.notNull(userDto, "userDto must not be null");
        return this.userRepository.save(UserUtil.createNewFromTo(userDto));
    }
    
    @Override
    public List<User> save(List<User> userList) {
        return this.userRepository.save(userList);
    }
    
    @Override
    public void delete(long id) {
        this.userRepository.delete(id);
    }
    
    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmailIgnoringCase(email);
    }
    
    @Override
    @Transactional
    public void enable(long id, boolean enabled) {
        User user = this.userRepository.findOne(id);
        user.setEnabled(enabled);
        this.userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void update(UserDto userDto) {
        User user = updateFromTo(this.userRepository.findOne(userDto.getId()), userDto);
        this.userRepository.save(prepareToSave(user));
    }
    
    @Override
    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        this.userRepository.save(prepareToSave(user));
    }
    
    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmailIgnoringCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
