package com.uran.dto;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
public class HorseDto implements Serializable {
    private static final long serialVersionUID = 5417510729237127721L;
    
    private Long id;
    
    @Size(min = 2, max = 25)
    @NotEmpty(message = "  is required")
    private String name;
    
    @Size(min = 2, max = 25)
    @NotEmpty(message = "  is required")
    private String ruName;
    
    @NotNull(message = "  must not be empty, minimum value - 1")
    @DecimalMin("1")
    private Integer age;
    
    public HorseDto(Long id, String name, String ruName, Integer age) {
        this.id = id;
        this.name = name;
        this.ruName = ruName;
        this.age = age;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    /*public boolean isNew() {
        return id == null;
    }*/
    
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
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "HorseDTO{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", ruName='" + ruName + '\''
                + ", age=" + age
                + '}';
    }
}