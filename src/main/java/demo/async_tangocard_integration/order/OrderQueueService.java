package demo.async_tangocard_integration.order;

import demo.async_tangocard_integration.lib.message_queue.MessageQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderQueueService {

    private final MessageQueue<Long> orderMessageQueue;

    public void queueOrder(Long orderId) {
        orderMessageQueue.addMessage(orderId);
    }

}
