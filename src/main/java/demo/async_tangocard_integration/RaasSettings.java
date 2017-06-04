package demo.async_tangocard_integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "raas")
public class RaasSettings {
    
    private String customerIdentifier;
    private String accountIdentifier;
    private String utid;
    
}
