package demo.async_tangocard_integration.order_queue;

import demo.async_tangocard_integration.lib.message_queue.MessageQueue;
import demo.async_tangocard_integration.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class OrderQueueWorker {
    
    private final MessageQueue<Long> orderMessageQueue;
    private final OrderService orderService;
    
    @Async
    public void startWorking() {
        while (true) {
            // Wrap work in a try/catch since Spring will stop async thread if uncaught exceptions are thrown
            try {
                work();
            }
            catch (Exception e) {
                log.error("Failed to work order queue: " + e.getMessage(), e);
            }
        }
    }
    
    private void work() {
        Long orderId = orderMessageQueue.pop();
        if (orderId == null) {
            log.info("Order queue is empty... sleeping");
            sleep();
        } else {
            orderService.processOrder(orderId);
        }
    }
    
    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to sleep worker thread");
        }
    }
    
}
