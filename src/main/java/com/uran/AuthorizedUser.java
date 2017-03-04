package com.uran;

import com.uran.domain.User;
import com.uran.dto.UserDto;
import com.uran.util.user.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User implements Serializable {
    private static final long serialVersionUID = 8512645918407459274L;
    
    private UserDto userDto;
    
    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userDto = UserUtil.asTo(user);
    }
    
    private static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }
    
    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }
    
    public static Long id() {
        return get().userDto.getId();
    }
    
    public void update(UserDto newDto) {
        userDto = newDto;
    }
    
    public UserDto getUserDto() {
        return userDto;
    }
    
    @Override
    public String toString() {
        return userDto.toString();
    }
}