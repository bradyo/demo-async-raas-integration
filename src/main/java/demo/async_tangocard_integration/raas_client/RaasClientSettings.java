package demo.async_tangocard_integration.raas_client;

import lombok.Data;

@Data
public class RaasClientSettings {

    private String baseUrl;
    private String platformName;
    private String apiKey;

}
