package demo.async_tangocard_integration.lib.message_queue;

import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class InMemoryMessageQueue<T> implements MessageQueue<T> {
    
    private final ConcurrentLinkedQueue<T> queue;

    @Override
    public void addMessage(T message) {
        queue.add(message);
    }

    @Override
    public T getMessage() {
        try {
            return queue.peek();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    
    @Override
    public void deleteMessage(T message) {
        try {
            queue.remove(message);
        } catch (NoSuchElementException e) {
            // Move along, nothing to see here...
        }
    }
}
