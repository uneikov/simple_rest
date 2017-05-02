package com.uran.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = -6600159339406192957L;
    
    private Long id;

    @NotEmpty
    //@SafeHtml
    @Size( min = 16, max = 16, message = " must have 16 digits")
    private String cardNumber;

    @NotEmpty
    //@SafeHtml
    private String name;
    
    @Email
    @NotEmpty
    //@SafeHtml
    private String email;

    //@SafeHtml
    @Size(min = 5, max = 64, message = " must between 5 and 64 characters")
    private String password;
    
    public UserDto(Long id, String cardNumber, String name, String email, String password) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isNew() {
        return id == null;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}