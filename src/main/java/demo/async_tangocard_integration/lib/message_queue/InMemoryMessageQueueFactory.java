package demo.async_tangocard_integration.lib.message_queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryMessageQueueFactory<T>  {

    public MessageQueue<T> create() {
        ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
        return new InMemoryMessageQueue<>(queue);
    }

}
