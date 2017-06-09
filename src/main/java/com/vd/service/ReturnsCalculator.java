package com.vd.service;

import com.vd.model.Deal;
import com.vd.model.Investment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ReturnsCalculator {

    @Autowired
    private DealRepository dealRepository;

    @GetMapping(path = "/returnsOfANumberOfDealsWithDifferentInput")
    public List<BigDecimal> calculateReturnsOfANumberOfDealsWithDifferentInput(List<Pair<String, BigDecimal>> dealId2Amount, int durationInYears) {
        return dealId2Amount.stream().map(dealIdAndAmount -> dealRepository.findOne(dealIdAndAmount.getFirst()).
                calculateReturn(dealIdAndAmount.getSecond(), durationInYears))
                .collect(toList());
    }
}
