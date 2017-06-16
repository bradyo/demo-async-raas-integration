package demo.async_tangocard_integration.raas_client;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * This is a RaasClient that hits an actual HTTP server.
 */
@AllArgsConstructor
public class HttpRaasClient implements RaasClient {

    private final RestTemplate restTemplate;
    
    public RaasOrder createOrder(RaasOrderCriteria raasOrderCriteria) {
        try {
            return restTemplate.postForObject("/orders", raasOrderCriteria, RaasOrder.class);
        } catch (Exception e) {
            throw new OrderFailedException(e);
        }
    }
}
