package com.uran.service;

import com.uran.AuthorizedUser;
import com.uran.domain.User;
import com.uran.dto.UserDto;
import com.uran.repository.UserRepository;
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
    @Autowired
    private UserRepository repository;
    
    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }
    
    @Override
    public User findOne(long id) {
        return this.repository.findOne(id);
    }
    
    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return this.repository.save(user);
    }
    
    @Override
    public List<User> save(List<User> userList) {
        return this.repository.save(userList);
    }
    
    @Override
    public void delete(long id) {
        this.repository.delete(id);
    }
    
    @Override
    public User findByEmail(String email) {
        return this.repository.findByEmailIgnoringCase(email);
    }
    
    @Override
    @Transactional
    public void enable(long id, boolean enabled) {
        User user = this.repository.findOne(id);
        user.setEnabled(enabled);
        this.repository.save(user);
    }
    
    @Override
    @Transactional
    public void update(UserDto userDto) {
        User user = updateFromTo(this.repository.findOne(userDto.getId()), userDto);
        this.repository.save(prepareToSave(user));
    }
    
    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        this.repository.save(prepareToSave(user));
    }
    
    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.repository.findByEmailIgnoringCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
