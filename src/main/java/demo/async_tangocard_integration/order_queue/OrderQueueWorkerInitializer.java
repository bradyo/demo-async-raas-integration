package demo.async_tangocard_integration.order_queue;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderQueueWorkerInitializer {

    private final OrderQueueWorker orderQueueWorker;
    
    @PostConstruct
    public void init() {
        orderQueueWorker.startWorking();
    }

}
