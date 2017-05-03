package com.uran.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ToString(exclude={"id", "user"})
@Table(name = "accounts")
@NoArgsConstructor
public class Account implements Serializable{
    private static final long serialVersionUID = -6091983356638366235L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Card number is required.")
    @CreditCardNumber
    @Column
    private String cardNumber;
    
    @Column(nullable = false)
    private Double balance = 0.0;
    
    @OneToOne(optional = false)
    private User user;
    
    public Account(String cardNumber, Double balance, User user) {
        this(null, cardNumber, balance, user);
    }
    
    private Account(Long id, String cardNumber, Double balance, User user) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
