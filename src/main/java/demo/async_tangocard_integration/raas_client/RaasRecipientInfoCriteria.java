package demo.async_tangocard_integration.raas_client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RaasRecipientInfoCriteria {

    private String email;
    private String firstName;
    private String lastName;

}
