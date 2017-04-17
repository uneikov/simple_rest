package com.uran.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@ToString(exclude={"id","horse","user","race"})
@Table(name = "stakes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "datetime") })
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Stake implements Serializable{
    private static final long serialVersionUID = 5060337295162113935L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private Double stakeValue;
    
    @NaturalId
    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime = LocalDateTime.now();
    
    @Column(nullable = false)
    private boolean wins;
    
    @Column(nullable = false)
    private Double amount;
    
    @Column(nullable = false)
    private boolean editable;
    
    @NotNull
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private @Setter @Getter Horse horse;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //@JsonBackReference(value="stake-user")
    private User user;
    
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //@JsonBackReference(value="stake-race")
    private @Setter @Getter Race race;
    
    public Stake() {
    }
    
    public Stake(Double stakeValue, boolean wins, Double amount) {
        this.stakeValue = stakeValue;
        this.wins = wins;
        this.amount = amount;
        this.editable = true;
    }
    
    public Stake(Long id, Double stakeValue, boolean wins, Double amount) {
        this.id = id;
        this.stakeValue = stakeValue;
        this.wins = wins;
        this.amount = amount;
        this.editable = true;
    }
    
    public Stake(Long id, User user, Horse horse, Double stakeValue, boolean wins, Double amount) {
        this.id = id;
        this.user = user;
        this.horse = horse;
        this.stakeValue = stakeValue;
        this.wins = wins;
        this.amount = amount;
        this.editable = true;
    }
    
    public Stake(Long id, User user, Horse horse, Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount, boolean editable) {
        this.id = id;
        this.user = user;
        this.horse = horse;
        this.stakeValue = stakeValue;
        this.dateTime = dateTime;
        this.wins = wins;
        this.amount = amount;
        this.editable = editable;
    }
    
    public Stake(Long id, User user, Horse horse, Race race, Double stakeValue, LocalDateTime dateTime, boolean wins, Double amount, boolean editable) {
        this.id = id;
        this.user = user;
        this.horse = horse;
        this.race = race;
        this.stakeValue = stakeValue;
        this.dateTime = dateTime;
        this.wins = wins;
        this.amount = amount;
        this.editable = editable;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isEditable() {
        return editable;
    }
    
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public Double getStakeValue() {
        return stakeValue;
    }
    
    public void setStakeValue(Double stakeValue) {
        this.stakeValue = stakeValue;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public boolean isWins() {
        return wins;
    }
    
    public void setWins(boolean wins) {
        this.wins = wins;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
