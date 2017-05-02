package com.uran.web.controller;

import com.uran.domain.User;
import com.uran.dto.UserDto;
import com.uran.service.AccountService;
import com.uran.service.UserService;
import com.uran.util.user.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    private UserService userService;

    private AccountService accountService;

    private ModelMapper modelMapper;

    @Autowired
    public UserRestController(UserService userService, AccountService accountService, ModelMapper modelMapper) {
        this.userService = userService;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto addUser(@Valid @RequestBody final UserDto userDto) {
        final User added = this.userService.save(convertToEntity(userDto));
        this.accountService.save(added.getAccount());
        return convertToDto(added);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
       /* PropertyMap<User, UserDto> _userMap = new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                map().setCardNumber(source.getAccount().getCardNumber());
            }
        };
        User mappped = modelMapper.map(userDto, User.class);
        PropertyMap<UserDto, User> userMap = new PropertyMap<UserDto, User>() {
            @Override
            protected void configure() {
                map().setAccount(new Account(source.getCardNumber(), 0.0, mappped));
            }
        };
        modelMapper.addMappings(_userMap);
        modelMapper.addMappings(userMap);

        return modelMapper.map(userDto, User.class);*/
        return UserUtil.createNewFromTo(userDto);
    }
}
