package com.vd.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

/**
 * Created by alis on 09/06/2017.
 */
public class InvestmentOnADeal extends ReturnCalculated {
    private String dealId;



    public InvestmentOnADeal(String dealId, BigDecimal investmentAmount) {
        this.dealId = dealId;
        this.investmentAmount = investmentAmount;
    }

    public InvestmentOnADeal() {
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

}
