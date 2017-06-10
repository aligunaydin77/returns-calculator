package com.vd.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;


public class InvestmentOnADeal  {

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal investmentAmount;
    private String dealId;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }

    public InvestmentOnADeal(String dealId, BigDecimal investmentAmount) {
        this.dealId = dealId;
        this.investmentAmount = investmentAmount;
    }

    public InvestmentOnADeal() {
    }

    public String getDealId() {
        return dealId;
    }

}
