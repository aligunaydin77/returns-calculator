package com.vd.web;

import com.vd.fx.ReturnInDolar;
import com.vd.model.Client;
import com.vd.model.Deal;
import com.vd.service.ClientRepository;
import com.vd.service.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
public class ReturnsCalculator {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReturnInDolar returnInDolar;

    @PostMapping(path = "/returnsOfANumberOfDealsWithDifferentInput")
    public  @ResponseBody
    List<ReturnCalculated> calculateReturnsOfANumberOfDealsWithDifferentInput(@RequestBody List<InvestmentOnADeal> dealId2Amount,
                                                                        @RequestParam(name = "durationInYears") int durationInYears) {
        return dealId2Amount.stream()
                .filter(investmentOnADeal -> dealRepository.findOne(investmentOnADeal.getDealId()) != null)
                .map(investmentOnADeal -> dealRepository.findOne(investmentOnADeal.getDealId()).
                                calculateReturn(investmentOnADeal.getInvestmentAmount(), durationInYears, returnInDolar))
                .map(ReturnCalculated::fromBigDecimal)
                .collect(toList());
    }

    @GetMapping(path = "/totalReturnOfClient")
    public @ResponseBody ReturnCalculated calculateTotalReturnOfAClient(@RequestParam(name = "clientId") String clientId,
                                                                        @RequestParam(name = "durationInYears") int durationInYears ) {
        Client client = clientRepository.findOne(clientId);
        if(client == null) {
            throw new ClientNotFoundException(clientId);
        }
        if(client.getListOfInvestment() == null) {
            return new ReturnCalculated(BigDecimal.ZERO);
        }
        Optional<BigDecimal> totalReturn = client.getListOfInvestment()
                .stream()
                .map(investment -> investment.calculateReturn( durationInYears, returnInDolar))
                .reduce((return1, return2) -> return1.add(return2));
        if(totalReturn.isPresent()) {
            return new ReturnCalculated(totalReturn.get());
        }
        return new ReturnCalculated(BigDecimal.ZERO);
    }

    @GetMapping(path = "/allDeals")
    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }
}
