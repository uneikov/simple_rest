package com.uran.util.stake;

import com.uran.domain.Stake;

import java.util.List;
import java.util.stream.Collectors;

public class StakeUtil {

    public static Double getValuesSum(final List<Stake> stakes) {
        return stakes.stream().mapToDouble(Stake::getStakeValue).sum();
    }

    public static List<Stake> getFilteredByWins(final List<Stake> stakes, final String option) {
        final String filterOption = option == null ? "all" : option;

        switch (filterOption) {
            case "success":
                return stakes.stream().filter(Stake::isWins).collect(Collectors.toList());
            case "failure":
                return stakes.stream().filter(stake -> !stake.isWins()).collect(Collectors.toList());
            default:
                return stakes;
        }
    }

}
