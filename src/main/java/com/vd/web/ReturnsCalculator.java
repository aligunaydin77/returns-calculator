package com.vd.web;

import com.vd.service.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ReturnsCalculator {

    @Autowired
    private DealRepository dealRepository;

    @PostMapping(path = "/returnsOfANumberOfDealsWithDifferentInput")
    public  @ResponseBody
    List<ReturnCalculated> calculateReturnsOfANumberOfDealsWithDifferentInput(@RequestBody List<InvestmentOnADeal> dealId2Amount,
                                                                        @RequestParam(name = "durationInYears") int durationInYears) {
        return dealId2Amount.stream()
                .map(investmentOnADeal -> dealRepository.findOne(investmentOnADeal.getDealId()).
                    calculateReturn(investmentOnADeal.getInvestmentAmount(), durationInYears))
                .map(ReturnCalculated::fromBigDecimal)
                .collect(toList());
    }
}
