package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.raas_client.StubRaasClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class OrderControllerWebTest {

    @Autowired
    private StubReferenceNumberGenerator stubReferenceNumberGenerator;
    
    @Autowired
    private StubRaasClient stubRaasClient;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testOrderSuccess() throws Exception {
        String stubReferenceNumber = "0001-0000-000001";
        stubReferenceNumberGenerator.setStubValue(stubReferenceNumber);
        
        String stubRaasRefId = "test123";
        stubRaasClient.setOptionalStubRaasRefId(stubRaasRefId);
        
        String requestBody = "{\n" +
            "  \"amount\": \"100.00\"\n" +
            "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        
        ResponseEntity<String> response = testRestTemplate.postForEntity( "/orders", request, String.class);
        
        String expectedResponseBody = "{\n" +
            "  \"referenceNumber\" : \"0001-0000-000001\"\n" +
            "}";
        assertThat(response.getStatusCodeValue(), equalTo(200));
        assertThat(response.getBody(), equalTo(expectedResponseBody));
    }
}
