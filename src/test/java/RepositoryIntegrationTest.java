import com.vd.Application;
import com.vd.model.Client;
import com.vd.service.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RepositoryIntegrationTest extends TestBasics{

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldSaveClientWithItsDeals() throws Exception {
        Client client = getClient();
        clientRepository.save(client);
        assertThat(clientRepository.findAll().size(), equalTo(1));
    }

}
