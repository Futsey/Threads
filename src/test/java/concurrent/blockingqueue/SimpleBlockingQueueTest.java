package concurrent.blockingqueue;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 15; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)));
    }
}