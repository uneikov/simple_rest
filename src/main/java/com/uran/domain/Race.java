package com.uran.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString(exclude={"id", "stakes"})
@Table(name = "races", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "start") })
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Race implements Serializable{
    private static final long serialVersionUID = 7814524545785467671L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @NaturalId
    @Column(nullable = false)
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    
    @Column
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finish;
    
    // Horses list (serialized)
    @Column(nullable = false)
    private String horses;
    
    // Winning horse (serialized)
    @Column(nullable = false)
    private String winning;
    
    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonManagedReference(value="stake-race")
    private List<Stake> stakes;
    
    public Race(Long id, LocalDateTime startRace, LocalDateTime endRace) {
        this.id = id;
        this.start = startRace;
        this.finish = endRace;
    }
    
    public Race(Long id, LocalDateTime startRace, LocalDateTime endRace, String horses, String winning) {
        this.id = id;
        this.start = startRace;
        this.finish = endRace;
        this.horses = horses;
        this.winning = winning;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getStart() {
        return start;
    }
    
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    public LocalDateTime getFinish() {
        return finish;
    }
    
    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }
    
    public String getHorses() {
        return horses;
    }
    
    public void setHorses(String horses) {
        this.horses = horses;
    }
    
    public String getWinning() {
        return winning;
    }
    
    public void setWinning(String winning) {
        this.winning = winning;
    }
    
    public List<Stake> getStakes() {
        return stakes;
    }
    
    public void setStakes(List<Stake> stakes) {
        this.stakes = stakes;
    }
}
