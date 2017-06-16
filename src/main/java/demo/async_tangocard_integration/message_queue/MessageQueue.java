package demo.async_tangocard_integration.message_queue;

public interface MessageQueue<T> {
    void addMessage(T message);
    T getMessage();
    void deleteMessage(T message);
}
