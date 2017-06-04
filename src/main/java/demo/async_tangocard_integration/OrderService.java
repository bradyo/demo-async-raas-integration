package demo.async_tangocard_integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {
    
    private final ReferenceNumberGenerator referenceNumberGenerator;
    private final OrderRepository orderRepository;
    
    public Order placeOrder(User user, BigDecimal amount) {
        String referenceNumber = referenceNumberGenerator.generate();
        
        Order order = new Order();
        order.setUser(user);
        order.setAmount(amount);
        order.setReferenceNumber(referenceNumber);
        order.setStatus(OrderStatus.NEW);
        order.setTries(0);
        
        // Generate a unique externalId value that is used to de-dupe RaaS order requests.
        order.setTangoCardExternalId(UUID.randomUUID().toString());
        
        orderRepository.save(order);
        
        return order;
    }
}
