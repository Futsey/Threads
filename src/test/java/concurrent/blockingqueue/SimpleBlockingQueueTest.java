package concurrent.blockingqueue;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenAddNewValueSuccesfull() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue(3);
        Thread producer = new Thread(() -> {
            try {
                simpleBlockingQueue.offer(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
        SimpleBlockingQueue<Integer> simpleBlockingQueue1 = new SimpleBlockingQueue(3);
        Thread producer = new Thread(() -> {
            try {
                simpleBlockingQueue1.offer(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                simpleBlockingQueue1.offer(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                simpleBlockingQueue1.offer(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                simpleBlockingQueue1.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                simpleBlockingQueue1.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        int actualElement = 3;
        producer.start();
        consumer.start();
        producer.join();
        consumer.join(100);
        assertThat(actualElement).isEqualTo(simpleBlockingQueue1.getElement());
    }
}