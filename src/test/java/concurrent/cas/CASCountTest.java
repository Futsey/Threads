package concurrent.cas;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CASCountTest {

    @Test
    public void whenSuccesfullIncrement() throws InterruptedException {
        CASCount casCount = new CASCount(100);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1; i++) {
                casCount.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                casCount.increment();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                casCount.increment();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                casCount.increment();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        assertThat(casCount.get(), is(110));
    }
}