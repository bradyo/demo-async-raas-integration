package demo.async_tangocard_integration.lib.raas_client;

import lombok.Data;

@Data
public class RaasClientSettings {

    private String baseUrl;
    private String platformName;
    private String apiKey;

}
