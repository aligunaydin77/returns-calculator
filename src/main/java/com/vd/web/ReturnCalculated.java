package com.vd.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

public class ReturnCalculated {

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal returnOfInvestment;

    public void setReturnOfInvestment(BigDecimal returnOfInvestment) {
        this.returnOfInvestment = returnOfInvestment;
    }

    public ReturnCalculated(BigDecimal returnOfInvestment) {
        this.returnOfInvestment = returnOfInvestment;
    }

    public BigDecimal getReturnOfInvestment() {
        return returnOfInvestment;
    }

    public ReturnCalculated() {
    }

    public static ReturnCalculated fromBigDecimal(BigDecimal investmentAmount) {
        return new ReturnCalculated(investmentAmount);
    }

}
