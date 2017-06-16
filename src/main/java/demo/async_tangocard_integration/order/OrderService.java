package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.config.RaasSettings;
import demo.async_tangocard_integration.user.User;
import demo.async_tangocard_integration.raas_client.RaasClient;
import demo.async_tangocard_integration.raas_client.RaasOrder;
import demo.async_tangocard_integration.raas_client.RaasOrderCriteria;
import demo.async_tangocard_integration.raas_client.RaasRecipientInfoCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class OrderService {
    
    private final ReferenceNumberGenerator referenceNumberGenerator;
    private final OrderRepository orderRepository;
    private final RaasClient raasClient;
    private final RaasSettings raasSettings;

    public Order placeOrder(User user, BigDecimal amount) {
        // This is our public facing order reference number. We might show this to the customer
        // showing confirmation that the order has been placed.
        String referenceNumber = referenceNumberGenerator.generate();

        // Generate a unique externalId value that is used to de-dupe RaaS order requests.
        String raasExternalId = UUID.randomUUID().toString();
        
        Order order = Order.builder()
            .raasExternalId(raasExternalId)
            .user(user)
            .amount(amount)
            .referenceNumber(referenceNumber)
            .status(OrderStatus.NEW)
            .tries(0)
            .build();
        
        orderRepository.save(order);
        
        return order;
    }

    public void processOrder(Long orderId) {
        Order order = orderRepository.findOneForUpdate(orderId);
        if (order == null) {
            log.info("Order not found with id = " + orderId);
            return;
        }

        // Skip processing this order if it is already processed (for example, by another thread or node)
        if (! order.getStatus().equals(OrderStatus.NEW)) {
            return;
        }

        // Send the actual RaaS order request
        try {
            RaasOrderCriteria raasOrderCriteria = RaasOrderCriteria.builder()
                .externalRefID(order.getRaasExternalId())
                .customerIdentifier(raasSettings.getCustomerIdentifier())
                .accountIdentifier(raasSettings.getAccountIdentifier())
                .utid(raasSettings.getUtid())
                .amount(order.getAmount().doubleValue())
                .recipient(
                    RaasRecipientInfoCriteria.builder()
                    .email(order.getUser().getEmailAddress())
                    .firstName(order.getUser().getName())
                    .build()
                )
                .sendEmail(true) // send reward email though RaaS
                .build();

            RaasOrder raasOrder = raasClient.createOrder(raasOrderCriteria);

            order.setStatus(OrderStatus.PROCESSED);
            order.setRaasOrderRefId(raasOrder.getReferenceOrderID());

            log.info("Processed Raas order with orderRefID: " + raasOrder.getReferenceOrderID());
        }
        catch (HttpClientErrorException e) {
            // Client error indicates that the request is malformed, so we should stop trying to process.
            // These failed orders will need to be handled by some alternate process, for example by manual
            // review by engineering.
            order.setStatus(OrderStatus.FAILED);

            log.error("Client error on RaaS request: " + e.getResponseBodyAsString());
        }
        catch (HttpServerErrorException e) {
            // Server error indicates something went wrong on RaaS side. We can just not update the status
            // and allow this to be re-processed later.
            log.error("Server error on RaaS request: " + e.getResponseBodyAsString());
        }

        // Record the number of times this order has tried to be processed
        order.setTries(order.getTries() + 1);

        orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAllWithUsers();
    }
}
