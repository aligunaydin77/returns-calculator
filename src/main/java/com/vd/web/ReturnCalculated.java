package com.vd.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

/**
 * Created by alis on 09/06/2017.
 */
public class ReturnCalculated {

    @JsonSerialize(using = MoneySerializer.class)
    protected BigDecimal investmentAmount;

    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public ReturnCalculated(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public ReturnCalculated() {
    }

    public static ReturnCalculated fromBigDecimal(BigDecimal investmentAmount) {
        return new ReturnCalculated(investmentAmount);
    }
}
