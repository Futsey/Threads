package concurrent.blockingqueue;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenAddNewValueSuccesfull() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue();
        Thread producer = new Thread(() -> {
            simpleBlockingQueue.offer(1);
        });
        Thread consumer = new Thread(() -> {
            simpleBlockingQueue.poll();
        });
        int actualElement = 1;
        int expectedElement = 1;
        producer.start();
        producer.join();
        consumer.start();
        consumer.join(100);
        assertThat(actualElement).isEqualTo(expectedElement);
    }

    @Test
    public void whenAddSeveralNewValuesSuccesfull() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue1 = new SimpleBlockingQueue();
        Thread producer = new Thread(() -> {
            simpleBlockingQueue1.offer(1);
            simpleBlockingQueue1.offer(2);
            simpleBlockingQueue1.offer(3);
        });
        Thread consumer = new Thread(() -> {
            simpleBlockingQueue1.poll();
            simpleBlockingQueue1.poll();
        });
        int actualElement = 3;
        producer.start();
        consumer.start();
        producer.join();
        consumer.join(100);
        assertThat(actualElement).isEqualTo(simpleBlockingQueue1.getElement());
    }
}