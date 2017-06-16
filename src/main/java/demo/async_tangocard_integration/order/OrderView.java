package demo.async_tangocard_integration.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderView {

    private String referenceNumber;
    
}
