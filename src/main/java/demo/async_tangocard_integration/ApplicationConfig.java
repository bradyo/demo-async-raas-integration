package demo.async_tangocard_integration;

import demo.async_tangocard_integration.message_queue.InMemoryMessageQueueFactory;
import demo.async_tangocard_integration.message_queue.MessageQueue;
import demo.async_tangocard_integration.raas_client.RaasClient;
import demo.async_tangocard_integration.raas_client.RaasClientFactory;
import demo.async_tangocard_integration.raas_client.RaasClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public MessageQueue<Long> orderMessageQueue() {
        return new InMemoryMessageQueueFactory<Long>().create();
    }
    
    @Bean
    public RaasClient raasClient(Environment environment) {
        RaasClientSettings settings = new RaasClientSettings();
        settings.setBaseUrl(environment.getProperty("raasClient.baseUrl"));
        settings.setPlatformName(environment.getProperty("raasClient.platformName"));
        settings.setApiKey(environment.getProperty("raasClient.apiKey"));
        
        return new RaasClientFactory().create(settings);
    }
}
