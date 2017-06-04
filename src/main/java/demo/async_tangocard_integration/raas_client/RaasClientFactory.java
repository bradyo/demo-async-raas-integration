package demo.async_tangocard_integration.raas_client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class RaasClientFactory {
    
    public RaasClient create(RaasClientSettings settings) {
        RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri(settings.getBaseUrl())
            .basicAuthorization(settings.getPlatformName(), settings.getApiKey())
            .build();
        
        return new RaasClient(restTemplate);
    }
    
}
