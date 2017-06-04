package demo.async_tangocard_integration;

import demo.async_tangocard_integration.message_queue.MessageQueue;
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

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class OrderProcessingService {
    
    private final MessageQueue<Long> orderMessageQueue;
    private final OrderRepository orderRepository;
    private final RaasClient raasClient;
    private final RaasSettings raasSettings;
    
    public void queueOrder(Long orderId) {
        orderMessageQueue.push(orderId);
    }
    
    @Transactional
    public void processOrder(Long orderId) {
        // todo: find one for update (lock)
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
            RaasOrderCriteria raasOrderCriteria = new RaasOrderCriteria();
            raasOrderCriteria.setExternalRefID(order.getTangoCardExternalId());
            raasOrderCriteria.setCustomerIdentifier(raasSettings.getCustomerIdentifier());
            raasOrderCriteria.setAccountIdentifier(raasSettings.getAccountIdentifier());
            raasOrderCriteria.setUtid(raasSettings.getUtid());
            raasOrderCriteria.setAmount(order.getAmount().doubleValue());

            RaasRecipientInfoCriteria recipient = new RaasRecipientInfoCriteria();
            recipient.setEmail(order.getUser().getEmailAddress());
            recipient.setFirstName(order.getUser().getName());
            raasOrderCriteria.setRecipient(recipient);

            raasOrderCriteria.setSendEmail(true); // send reward email though RaaS

            RaasOrder raasOrder = raasClient.postOrder(raasOrderCriteria);

            order.setStatus(OrderStatus.PROCESSED);
            order.setTangoCardOrderRefId(raasOrder.getReferenceOrderID());
            
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
}
