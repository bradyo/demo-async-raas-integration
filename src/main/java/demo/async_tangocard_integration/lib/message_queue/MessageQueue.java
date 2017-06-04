package demo.async_tangocard_integration.lib.message_queue;

public interface MessageQueue<T> {
    void push(T item);
    T pop();
}
