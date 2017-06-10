import com.vd.fx.ReturnInDolar;
import com.vd.model.Client;
import com.vd.model.CompoundInterest;
import com.vd.model.Investment;
import com.vd.model.SimpleInterest;
import com.vd.service.ClientRepository;
import com.vd.service.DealRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


public class TestBasics {

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected DealRepository dealRepository;

    @Autowired
    protected TestRestTemplate restTemplate;

    @MockBean
    private ReturnInDolar returnInDolar;

    @Before
    public void setUp() throws Exception {
        clientRepository.save(getClient());
        Client client = new Client();
        client.setName("cannot afford");
        clientRepository.save(client);
        when(returnInDolar.getFxRate(any())).thenReturn(BigDecimal.TEN);
    }

    @After
    public void tearDown() throws Exception {
        clientRepository.deleteAll();
        dealRepository.deleteAll();
    }



    protected Client getClient() {

        persistDeals(); // this is to imitate that deals are already in place and user is able to select any/some of the deals

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
        deal1.setCurrency("JPY");
        dealRepository.save(deal1);

        CompoundInterest deal2 = new CompoundInterest();
        deal2.setAnnualInterestRate(new BigDecimal("0.3"));
        deal2.setCompoundingPeriodsPerYear(4);
        deal2.setCurrency("TRY");
        dealRepository.save(deal2);

        SimpleInterest deal3 = new SimpleInterest();
        deal3.setAnnualInterestRate(new BigDecimal("2.9"));
        deal3.setCurrency("GBP");
        dealRepository.save(deal3);

        CompoundInterest deal4 = new CompoundInterest();
        deal4.setAnnualInterestRate(new BigDecimal("4.7"));
        deal4.setCompoundingPeriodsPerYear(2);
        deal4.setCurrency("EUR");
        dealRepository.save(deal4);

        SimpleInterest deal5 = new SimpleInterest();
        deal5.setAnnualInterestRate(new BigDecimal("3.78"));
        deal5.setCurrency("CAD");
        dealRepository.save(deal5);

    }
}
