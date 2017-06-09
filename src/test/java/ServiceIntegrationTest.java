import com.vd.Application;
import com.vd.model.Client;
import com.vd.model.CompoundInterest;
import com.vd.model.SimpleInterest;
import com.vd.service.ClientRepository;
import com.vd.service.DealRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceIntegrationTest extends TestBasics {

    @Autowired
    private ClientRepository clientRepository;

    @Before
    public void setUp() throws Exception {
        clientRepository.save(getClient());
    }

    @Test
    public void shouldCalculateTheReturnsOfANumberOfDifferentKindsOfDealsWithDifferentInputs() throws Exception {

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
