package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.user.UserView;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FullOrderView {

    private BigDecimal amount;
    private String tangoCardExternalId;
    private String tangoCardOrderRefId;
    private Integer tries;
    private String status;

    public String referenceNumber;
    
    private UserView user;
    
}
