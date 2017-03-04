package com.uran.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;

@Entity
@ToString(exclude="id")
@NoArgsConstructor
@Table(name = "horses", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "name") })
public class Horse implements Serializable{
    private static final long serialVersionUID = 2714600491390664126L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @NotEmpty
    @NaturalId
    @Column(nullable = false)
    private String name;
    
    @NotEmpty
    @Column(nullable = false)
    private String ruName;
    
    @Digits(fraction = 0, integer = 2)
    @Column(nullable = false)
    private int age;
    
    @Digits(fraction = 0, integer = 4)
    @Column(nullable = false)
    private int wins;
    
    @Column(nullable = false)
    //@JsonProperty("readyForRace")
    private boolean ready;
    
    public Horse(String name, String ruName, int age, int wins) {
        this(null, name, ruName, age, wins);
    }
    
    public Horse(Long id, String name, String ruName, int age, int wins) {
        this.id = id;
        this.name = name;
        this.ruName = ruName;
        this.age = age;
        this.wins = wins;
        this.ready = false;
    }
    
    public Horse(Long id, String name, String ruName, int age, int wins, boolean ready) {
        this.id = id;
        this.name = name;
        this.ruName = ruName;
        this.age = age;
        this.wins = wins;
        this.ready = ready;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isReady() {
        return ready;
    }
    
    public void setReady(boolean ready) {
        this.ready = ready;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRuName() {
        return ruName;
    }
    
    public void setRuName(String ruName) {
        this.ruName = ruName;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getWins() {
        return wins;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }
}
