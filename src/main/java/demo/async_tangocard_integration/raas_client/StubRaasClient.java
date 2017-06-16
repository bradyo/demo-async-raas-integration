package demo.async_tangocard_integration.raas_client;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Optional;

/**
 * RaasClient for use during development and integration tests. 
 * 
 * - You can cause this client to throw an exception while ordering using `setOptionalException()` method.
 * - You can also set the returned RaaS Ref ID using `setOptionalStubRaasRefId()` method
 */
public class StubRaasClient implements RaasClient {

    private Optional<String> optionalStubRaasRefId = Optional.empty();
    private Optional<OrderFailedException> optionalException = Optional.empty();
    
    public RaasOrder createOrder(RaasOrderCriteria raasOrderCriteria) {
        if (optionalException.isPresent()) {
            throw optionalException.get();
        }
        
        RaasOrder raasOrder = new RaasOrder();
        raasOrder.setReferenceOrderID(generateRaasRefID());

        return raasOrder;
    }

    public void setOptionalStubRaasRefId(String value) {
        this.optionalStubRaasRefId = Optional.ofNullable(value);
    }
    
    public void unsetOptionalStubRaasRefId() {
        this.optionalStubRaasRefId = Optional.empty();
    }
    
    public void setException(OrderFailedException exception) {
        this.optionalException = Optional.ofNullable(exception);
    }
    
    public void unsetException() {
        this.optionalException = Optional.empty();
    }

    private String generateRaasRefID() {
        return "STUB-" + RandomStringUtils.random(6, false, true);
    }
}
