package com.vd.model;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class CompoundInterest extends Deal {

    private int compoundingPeriodsPerYear;

    public int getCompoundingPeriodsPerYear() {
        return compoundingPeriodsPerYear;
    }

    public void setCompoundingPeriodsPerYear(int compoundingPeriodsPerYear) {
        this.compoundingPeriodsPerYear = compoundingPeriodsPerYear;
    }

    @Override
    public BigDecimal calculateReturn(BigDecimal initialInvestmentAmount, int durationInYears) {
        return initialInvestmentAmount.
                multiply(BigDecimal.ONE.add(
                        getAnnualInterestRate().
                                divide(
                                        new BigDecimal(compoundingPeriodsPerYear)
                                )
                        ).pow (compoundingPeriodsPerYear * durationInYears )
                ).subtract(initialInvestmentAmount);
    }

}
