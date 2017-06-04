package demo.async_tangocard_integration.lib.raas_client;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class RaasClient {

    private final RestTemplate restTemplate;
    
    public RaasOrder postOrder(RaasOrderCriteria raasOrderCriteria) {
        return restTemplate.postForObject("/orders", raasOrderCriteria, RaasOrder.class);
    }
}
