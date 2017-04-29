package com.uran.dto;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
public class StakeDto implements Serializable{
    private static final long serialVersionUID = 6877055670850941808L;
    
    @NotNull(message = "  must not be empty")
    private Double stakeValue;

    @Size(min = 3, max = 60)
    @NotEmpty(message = "  is required")
    private String fullHorseName;

    public StakeDto(Double stakeValue, String fullHorseName) {
        this.stakeValue = stakeValue;
        this.fullHorseName = fullHorseName;
    }
    
    public Double getStakeValue() {
        return stakeValue;
    }
    
    public void setStakeValue(Double stakeValue) {
        this.stakeValue = stakeValue;
    }
    
    public void setFullHorseName(String fullHorseName) {
        this.fullHorseName = fullHorseName;
    }
    
    public String getFullHorseName() {
        return fullHorseName;
    }
    
    @Override
    public String toString() {
        return "StakeDto{" +
                "stakeValue=" + stakeValue +
                ", fullHorseName='" + fullHorseName + '\'' +
                '}';
    }
}
