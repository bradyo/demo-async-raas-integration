package demo.async_tangocard_integration.order;

public class StubReferenceNumberGenerator implements ReferenceNumberGenerator {
    
    private String stubValue;
    
    public String generate() {
        return stubValue;
    }
    
    public void setStubValue(String value) {
        stubValue = value;
    }
}
