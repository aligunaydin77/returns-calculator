package com.vd.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vd.fx.ReturnInDolar;
import com.vd.web.InvestmentOnADeal;
import com.vd.web.MoneySerializer;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompoundInterest.class, name = "compoundInterest"),
        @JsonSubTypes.Type(value = SimpleInterest.class, name = "simpleInterest") })
public abstract class Deal {

    @Id
    private String id;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    @JsonSerialize(using = MoneySerializer.class)
    protected BigDecimal annualInterestRate;

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public abstract BigDecimal calculateReturn(BigDecimal initialInvestmentAmount, int durationInYears);

    public BigDecimal calculateReturn(BigDecimal initialInvestmentAmount, int durationInYears, ReturnInDolar returnInDolar) {
        return calculateReturn(initialInvestmentAmount, durationInYears).multiply(returnInDolar.getFxRate(currency));
    }

}
