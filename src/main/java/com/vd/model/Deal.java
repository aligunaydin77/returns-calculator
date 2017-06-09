package com.vd.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public abstract class Deal {

    @Id
    private String id;

    protected BigDecimal annualInterestRate;

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public abstract BigDecimal calculateReturn(BigDecimal initialInvestmentAmount, int durationInYears);
}
