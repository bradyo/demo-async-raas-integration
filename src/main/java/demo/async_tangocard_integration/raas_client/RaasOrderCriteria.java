package demo.async_tangocard_integration.raas_client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RaasOrderCriteria {

    private String externalRefID;
    private String customerIdentifier;
    private String accountIdentifier;
    private String utid;
    private Double amount;
    private RaasRecipientInfoCriteria recipient;
    private Boolean sendEmail;

}
