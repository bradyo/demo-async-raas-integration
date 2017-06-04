package demo.async_tangocard_integration.message_queue;

import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class InMemoryMessageQueue<T> implements MessageQueue<T> {
    
    private final ConcurrentLinkedQueue<T> queue;

    @Override
    public void push(T item) {
        queue.add(item);
    }

    @Override
    public T pop() {
        try {
            return queue.remove();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
