import com.vd.Application;
import com.vd.model.Client;
import com.vd.service.ClientRepository;
import com.vd.web.InvestmentOnADeal;
import com.vd.web.ReturnCalculated;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Application.class)
public class ServiceIntegrationTest extends TestBasics {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        clientRepository.save(getClient());
    }

    @Test
    public void shouldCalculateTheReturnsOfANumberOfDifferentKindsOfDealsWithDifferentInputs() throws Exception {

        List<InvestmentOnADeal>  investmentOnADealList = new ArrayList<>();
        investmentOnADealList.add(new InvestmentOnADeal(dealRepository.findAll().get(0).getId(), new BigDecimal("500")));
        investmentOnADealList.add(new InvestmentOnADeal(dealRepository.findAll().get(1).getId(), new BigDecimal("3000")));

        HttpEntity<List<InvestmentOnADeal>> requestEntity = new HttpEntity<>(investmentOnADealList);
        ReturnCalculated[] returns = restTemplate.exchange("/returnsOfANumberOfDealsWithDifferentInput?durationInYears=2", HttpMethod.POST,
                requestEntity, ReturnCalculated[].class).getBody();

        assertThat(returns.length, equalTo(2));
    }

    @Test
    public void shouldCalculateTotalReturnsInClientPortfolio() throws Exception {
        Client client = clientRepository.findAll().get(0);
        Optional<BigDecimal> totalReturn = client.getListOfInvestment()
                .stream()
                   .map(investment -> investment.calculateReturn( 2))
                   .reduce((bigDecimal1, bigDecimal2) -> bigDecimal1.add(bigDecimal2));
        assertThat(totalReturn.get().setScale(2, RoundingMode.HALF_DOWN),
                comparesEqualTo(new BigDecimal("4750.43")));
    }
    
    
}
