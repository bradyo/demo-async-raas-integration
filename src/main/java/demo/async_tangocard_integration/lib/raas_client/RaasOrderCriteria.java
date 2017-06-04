package demo.async_tangocard_integration.lib.raas_client;

import lombok.Data;

@Data
public class RaasOrderCriteria {

    private String externalRefID;
    private String customerIdentifier;
    private String accountIdentifier;
    private String utid;
    private Double amount;
    private RaasRecipientInfoCriteria recipient;
    private Boolean sendEmail;

}
