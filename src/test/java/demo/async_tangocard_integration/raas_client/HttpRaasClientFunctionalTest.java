package demo.async_tangocard_integration.raas_client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

/**
 * Functional tests for HttpRaasClient configured to hit the Raas API Sandbox. 
 * - These tests may be flakey since they hit an external service, which can have errors.
 * - These tests only need to be run when making changes to the HttpRaasClient class. 
 * 
 * This test is not run by default, to run enable test group when running tests:
 * mvn test -Dtest-groups=http-raas-client-functional-tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestPropertySource("/raas-client-functional-test.properties")
@IfProfileValue(name = "test-groups", values = {"http-raas-client-functional-tests"})
public class HttpRaasClientFunctionalTest {
    
    private RaasClient raasClient;
    
    private String testCustomerIdentifier = "testCustomer";
    private String testAccountIdentfiier = "testAccount";
    private String testUtid = "u112358";
    private String testRecipientName = "Test User";
    private String testRecipientEmail = "test@domain.com";
    
    @Autowired
    private Environment environment;
    
    @Before
    public void before() {
        RaasClientSettings settings = new RaasClientSettings();
        settings.setBaseUrl(environment.getProperty("raas.baseUrl"));
        settings.setPlatformName(environment.getProperty("raas.platformName"));
        settings.setApiKey(environment.getProperty("raas.apiKey"));

        raasClient = new HttpRaasClientFactory().create(settings);
    }
    
    @Test
    public void testSuccess() {
        RaasOrderCriteria raasOrderCriteria = new RaasOrderCriteria();
        raasOrderCriteria.setExternalRefID("test-" + UUID.randomUUID().toString());
        raasOrderCriteria.setCustomerIdentifier(testCustomerIdentifier);
        raasOrderCriteria.setAccountIdentifier(testAccountIdentfiier);
        raasOrderCriteria.setUtid(testUtid);
        raasOrderCriteria.setAmount(1.00);

        RaasRecipientInfoCriteria recipient = new RaasRecipientInfoCriteria();
        recipient.setEmail(testRecipientEmail);
        recipient.setFirstName(testRecipientName);
        raasOrderCriteria.setRecipient(recipient);
        raasOrderCriteria.setSendEmail(true);

        RaasOrder raasOrder = raasClient.createOrder(raasOrderCriteria);    
    
        assertThat(raasOrder.getReferenceOrderID(), not(isEmptyString()));
    }
    
    @Test
    public void testInvalidRequest() {
        try {
            RaasOrderCriteria raasOrderCriteria = new RaasOrderCriteria();
            RaasOrder raasOrder = raasClient.createOrder(raasOrderCriteria);
            fail("expected exception not thrown");
        } catch (OrderFailedException e) {
            // expected exception thrown, we will just let this pass but we could assert exception content
        }
    }
}
