package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.raas_client.StubRaasClient;
import demo.async_tangocard_integration.raas_client.RaasClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderControllerWebTestConfig {
    
    @Bean
    public StubReferenceNumberGenerator stubReferenceNumberGenerator() {
        return new StubReferenceNumberGenerator();
    }
    
    @Bean
    public ReferenceNumberGenerator referenceNumberGenerator() {
        return new StubReferenceNumberGenerator();
    }

    @Bean
    public StubRaasClient stubRaasClient() {
        return new StubRaasClient();        
    }

    @Bean
    public RaasClient raasClient(StubRaasClient stubRaasClient) {
        return stubRaasClient;
     
    }
}
