import com.vd.Application;
import com.vd.fx.ReturnInDolar;
import com.vd.model.Client;
import com.vd.model.Deal;
import com.vd.service.ClientRepository;
import com.vd.web.InvestmentOnADeal;
import com.vd.web.ReturnCalculated;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Application.class)
public class ServiceIntegrationTest extends TestBasics {


    @Test
    public void shouldPresentAllTheDeals() throws Exception {
        Deal[] allDeals = restTemplate.getForObject("/allDeals", Deal[].class);

        assertThat(Arrays.asList(allDeals), hasItem(
                        hasProperty("annualInterestRate", comparesEqualTo(new BigDecimal("1.20")))
                ));
        assertThat(Arrays.asList(allDeals),
                hasItem(allOf(
                        hasProperty("annualInterestRate", comparesEqualTo(new BigDecimal("0.30"))),
                        hasProperty("compoundingPeriodsPerYear", equalTo(4)))
                ));
        assertThat(Arrays.asList(allDeals), hasItem(
                hasProperty("annualInterestRate", comparesEqualTo(new BigDecimal("2.90")))
        ));
        assertThat(Arrays.asList(allDeals),
                hasItem(allOf(
                        hasProperty("annualInterestRate", comparesEqualTo(new BigDecimal("4.70"))),
                        hasProperty("compoundingPeriodsPerYear", equalTo(2)))
                ));
        assertThat(Arrays.asList(allDeals), hasItem(
                hasProperty("annualInterestRate", comparesEqualTo(new BigDecimal("3.78")))
        ));
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
        assertThat(Arrays.asList(returns), contains(
                hasProperty("returnOfInvestment", comparesEqualTo(new BigDecimal("12000.00"))),
                hasProperty("returnOfInvestment", comparesEqualTo(new BigDecimal("23504.33")))
        ));
    }

    @Test
    public void shouldCalculateTotalReturnsInClientPortfolio() throws Exception {
        BigDecimal totalReturn = restTemplate.getForObject("/totalReturnOfClient?clientId={clientId}&durationInYears=2",
                ReturnCalculated.class, clientRepository.findAll().get(0).getId()).getReturnOfInvestment();
        assertThat(totalReturn.setScale(2, RoundingMode.HALF_DOWN),
                comparesEqualTo(new BigDecimal("47504.33")));
    }

    @Test
    public void shouldGiveClearErrorWhenClientNotRecognized() throws Exception {
        ResponseEntity<String> errorResponse = restTemplate.getForEntity("/totalReturnOfClient?clientId={clientId}&durationInYears=2",
                String.class, "123");
        assertThat(errorResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        assertThat(errorResponse.getBody(), containsString("ClientNotFound"));
    }

    @Test
    public void shouldReturnZeroWhenClientHasNoInvestment() throws Exception {
        ResponseEntity<ReturnCalculated> positiveResponse = restTemplate.getForEntity("/totalReturnOfClient?clientId={clientId}&durationInYears=2",
                ReturnCalculated.class, clientRepository.findAll().get(1).getId());
        assertThat(positiveResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(positiveResponse.getBody().getReturnOfInvestment(), comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    public void shouldReturnAnEmptyListWhenDealsAreNotRecognized() throws Exception {
        List<InvestmentOnADeal>  investmentOnADealList = new ArrayList<>();
        investmentOnADealList.add(new InvestmentOnADeal("unrealDeal", new BigDecimal("500")));

        HttpEntity<List<InvestmentOnADeal>> requestEntity = new HttpEntity<>(investmentOnADealList);
        ResponseEntity<Object> responseEntity = restTemplate.exchange("/returnsOfANumberOfDealsWithDifferentInput?durationInYears=2", HttpMethod.POST,
                requestEntity, Object.class);
        List<ReturnCalculated> calculatedReturns = (List<ReturnCalculated>) responseEntity.getBody();
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(calculatedReturns.size(), equalTo(0));

    }
}
