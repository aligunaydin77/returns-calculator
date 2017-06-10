package com.vd.model;

import com.vd.fx.ReturnInDolar;

import java.math.BigDecimal;


public class Investment {

    private Deal deal;
    private BigDecimal initialInvestmentAmount;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

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

    public BigDecimal calculateReturn(int durationInYears, ReturnInDolar returnInDolar) {
        return deal.calculateReturn(initialInvestmentAmount, durationInYears, returnInDolar);
    }
}
