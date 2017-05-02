package com.uran.util.user;


import com.uran.domain.Account;
import com.uran.domain.Role;
import com.uran.domain.User;
import com.uran.dto.UserDto;
import com.uran.util.PasswordUtil;

public class UserUtil {
    public static User createNewFromTo(UserDto userDto) {
        User poorUser = new User(
                null,
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                null,
                Role.ROLE_USER
        );
        Account account = new Account(userDto.getCardNumber(), 0.0, poorUser);
        poorUser.setAccount(account);
        return poorUser;
    }
    
    public static UserDto asTo(User user) {
        return new UserDto(user.getId(), user.getAccount().getCardNumber(), user.getName(), user.getEmail(), user.getPassword());
    }
    
    public static User updateFromTo(User user, UserDto userDto) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
    
    public static User prepareToSave(User user) {
        /*if (user.getAccount() == null) {
            user.setAccount(new Account(user.getAccount().getCardNumber(), 0.0, user));
        }*/
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}