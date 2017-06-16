package demo.async_tangocard_integration;

import demo.async_tangocard_integration.order.ReferenceNumberGenerator;
import demo.async_tangocard_integration.order.StubReferenceNumberGenerator;
import demo.async_tangocard_integration.raas_client.StubRaasClient;
import demo.async_tangocard_integration.raas_client.RaasClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integration-test")
public class IntegrationTestConfig {
    
    @Bean
    public StubReferenceNumberGenerator stubReferenceNumberGenerator() {
        return new StubReferenceNumberGenerator();
    }
    
    @Bean
    @Primary
    public ReferenceNumberGenerator referenceNumberGenerator() {
        return stubReferenceNumberGenerator();
    }

    @Bean
    public StubRaasClient stubRaasClient() {
        return new StubRaasClient();        
    }

    @Bean
    @Primary
    public RaasClient raasClient() {
        return stubRaasClient();
    }
}
