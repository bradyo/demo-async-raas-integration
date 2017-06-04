package demo.async_tangocard_integration.order;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderPostValidator {
    
    private static final BigDecimal minAmount = new BigDecimal("1.00");
    private static final BigDecimal maxAmount = new BigDecimal("1000.00");
    
    public ValidatedOrderPost validate(OrderPost orderPost) {
        BigDecimal parsedAmount;
        try {
            parsedAmount = new BigDecimal(orderPost.getAmount());
        } catch (NumberFormatException e) {
            // todo: throw ValidationException
            throw new RuntimeException("Amount is not a valid number");
        }
        
        if (parsedAmount.compareTo(minAmount) < 0 || parsedAmount.compareTo(maxAmount) > 0) {
            // todo: throw ValidationException
            throw new RuntimeException("amount must be between " + minAmount.toPlainString() 
                + " and " + maxAmount.toPlainString());
        }
        
        ValidatedOrderPost validatedOrderPost = new ValidatedOrderPost();
        validatedOrderPost.setAmount(parsedAmount);
        
        return validatedOrderPost;
    }
}
