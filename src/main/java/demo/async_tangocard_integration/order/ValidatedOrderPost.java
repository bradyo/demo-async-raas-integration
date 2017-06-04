package demo.async_tangocard_integration.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ValidatedOrderPost {
    
    private BigDecimal amount;
    
}
