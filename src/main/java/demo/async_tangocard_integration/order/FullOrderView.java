package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.user.UserView;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FullOrderView {

    private Long id;
    private BigDecimal amount;
    private String raasExternalId;
    private String raasOrderRefId;
    private Integer tries;
    private String status;

    public String referenceNumber;
    
    private UserView user;
    
}
