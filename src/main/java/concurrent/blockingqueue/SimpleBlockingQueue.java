package concurrent.blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int queueSize;

    public SimpleBlockingQueue(int queueSize) {
        this.queueSize = queueSize;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= queueSize) {
            System.out.println("Queue is full. Waiting for consumer");
            wait();
        }
        System.out.println("Adding new value: " + value);
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() < 1) {
            System.out.println("Queue is empty. Nothing to delete");
            wait();
        }
        T removeObj = queue.poll();
        System.out.println("Value was removed: " + removeObj);
        notifyAll();
        return removeObj;
    }

    public synchronized T getElement() {
        return queue.element();
    }

    public boolean isEmpty() {
        return queue.peek() == null;
    }
}
