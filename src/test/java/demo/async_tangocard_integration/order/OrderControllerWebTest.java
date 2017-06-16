package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.config.ApplicationConfig;
import demo.async_tangocard_integration.raas_client.StubRaasClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, OrderControllerWebTestConfig.class})
@WebAppConfiguration
public class OrderControllerWebTest {

    @Autowired
    private StubReferenceNumberGenerator stubReferenceNumberGenerator;
    
    @Autowired
    private StubRaasClient stubRaasClient;
    
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }
    
    @Test
    public void testOrderSuccess() throws Exception {
        String stubReferenceNumber = "001-00-000001";
        stubReferenceNumberGenerator.setStubValue(stubReferenceNumber);
        
        String stubRaasRefId = "test123";
        stubRaasClient.setOptionalStubRaasRefId(stubRaasRefId);
        
        String requestBody = "{\n" +
            "  \"amount\": \"100.00\"\n" +
            "}";
        
        MockHttpServletResponse response = mvc
            .perform(
                post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
            .andReturn()
            .getResponse();
        
        String expectedResponseBody = "{\n" +
            "  \"referenceNumber\": \"" + stubReferenceNumber + "\"\n" +
            "}";
        
        assertThat(response.getStatus(), equalTo(200));
        assertThat(response.getContentAsString(), equalTo(expectedResponseBody));
    }
}
