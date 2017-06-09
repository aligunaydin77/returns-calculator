package com.vd.model;

import java.math.BigDecimal;


public class Investment {

    private Deal deal;
    private BigDecimal initialInvestmentAmount;

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public BigDecimal getInitialInvestmentAmount() {
        return initialInvestmentAmount;
    }

    public void setInitialInvestmentAmount(BigDecimal initialInvestmentAmount) {
        this.initialInvestmentAmount = initialInvestmentAmount;
    }

    public BigDecimal calculateReturn(int durationInYears) {
        return deal.calculateReturn(initialInvestmentAmount, durationInYears);
    }
}
