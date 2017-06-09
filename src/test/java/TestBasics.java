import com.vd.model.Client;
import com.vd.model.CompoundInterest;
import com.vd.model.Investment;
import com.vd.model.SimpleInterest;
import com.vd.service.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alis on 09/06/2017.
 */
public class TestBasics {

    @Autowired
    private DealRepository dealRepository;

    protected Client getClient() {

        persistDeals(); // this is to imitate that deals are already in place and user is able to select the any/some of the deals

        Client client = new Client();
        client.setName("someoneelse");
        List<Investment> listOfInvestments = new ArrayList<>();

        Investment investment1 = new Investment();
        investment1.setInitialInvestmentAmount(new BigDecimal("1000"));
        investment1.setDeal(dealRepository.findAll().get(0));
        listOfInvestments.add(investment1);

        Investment investment2 = new Investment();
        investment2.setInitialInvestmentAmount(new BigDecimal("3000"));
        investment2.setDeal(dealRepository.findAll().get(1));
        listOfInvestments.add(investment2);
        client.setListOfInvestment(listOfInvestments);
        return client;
    }

    private void persistDeals() {
        SimpleInterest deal1 = new SimpleInterest();
        deal1.setAnnualInterestRate(new BigDecimal("1.2"));
        dealRepository.save(deal1);

        CompoundInterest deal2 = new CompoundInterest();
        deal2.setAnnualInterestRate(new BigDecimal("0.3"));
        deal2.setCompoundingPeriodsPerYear(4);
        dealRepository.save(deal2);
    }
}
