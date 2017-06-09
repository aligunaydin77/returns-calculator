package com.vd.model;

import java.math.BigDecimal;

public class SimpleInterest extends Deal {

    @Override
    public BigDecimal calculateReturn(BigDecimal initialInvestmentAmount, int durationInYears) {
        return initialInvestmentAmount.multiply(annualInterestRate).multiply(BigDecimal.valueOf(durationInYears));
    }
}
