package com.uran.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@ToString(exclude={"id","password","registered","stakes", "account"})
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "email") })
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable{
    private static final long serialVersionUID = 1382889789079199885L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    @NotEmpty
    private String name;
    
    @Column(unique = true, nullable = false)
    @NaturalId
    @NotEmpty
    @Email
    private String email;
    
    @Column(nullable = false)
    @NotEmpty
    @Length(min = 5)
    private String password;
    
    @Column(nullable = false)
    private boolean enabled = true;
    
    @Column(columnDefinition = "timestamp default now()")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private @Setter @Getter Date registered = new Date();
    
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private Set<Role> roles;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonManagedReference(value="stake-user")
    private @Setter List<Stake> stakes;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;
    
    public User() {
    }
    
    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.isEnabled(), u.getAccount(),  u.getRoles());
    }
    
    public User(Long id, String name, String email, String password, Account account, Role role, Role... roles) {
        this(id, name, email, password, true, account, EnumSet.of(role, roles));
    }
    
    public User(Long id, String name, String email, String password, boolean enabled, Account account, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.account = account;
        setRoles(roles);
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }
}
