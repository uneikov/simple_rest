package com.uran.util.user;


import com.uran.domain.Account;
import com.uran.domain.Role;
import com.uran.domain.User;
import com.uran.dto.UserDto;
import com.uran.util.PasswordUtil;
import com.uran.util.RandomUtil;

public class UserUtil {
    
    public static User createNewFromTo(UserDto newUser) {
        User preUser = new User();
        Account account = new Account(RandomUtil.getRandomCardNumber(), 0.0, preUser);
        return new User(
                null,
                newUser.getName(),
                newUser.getEmail().toLowerCase(),
                newUser.getPassword(),
                account,
                Role.ROLE_USER
        );
    }
    
    public static UserDto asTo(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
    
    public static User updateFromTo(User user, UserDto userDto) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
    
    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}