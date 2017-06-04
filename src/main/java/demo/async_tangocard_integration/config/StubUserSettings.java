package demo.async_tangocard_integration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stubUser")
@Data
public class StubUserSettings {
    
    private String name;
    private String emailAddress;
    
}
