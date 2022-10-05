package concurrent.blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue;

    public SimpleBlockingQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void offer(T value) {
        while (queue.peek() != null) {
            try {
                System.out.println("Queue is full. Waiting for consumer");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Adding new value: " + value);
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() {
        while (queue.peek() == null) {
            try {
                System.out.println("Queue is empty. Nothing to delete");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        T removeObj = queue.poll();
        System.out.println("Value was removed: " + removeObj);
        notifyAll();
        return removeObj;
    }

    public synchronized T getElement() {
        return queue.element();
    }
}
