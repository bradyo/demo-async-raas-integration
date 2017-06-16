package demo.async_tangocard_integration.raas_client;

public class OrderFailedException extends RuntimeException {
    public OrderFailedException(Exception e) {
        super(e);
    }
}
