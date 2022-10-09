package concurrent.pool;

import concurrent.blockingqueue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс является простой реализацией пула потоков
 * В классе реализованы два основных метода:
 * добавление задачи
 * {@link #work(Runnable)}
 * остановка выполнения всех задач
 * {@link #shutDown()}
 * @author ANDREW PETRUSHIN (JOB4J Project)
 * @version 1.0
 */
public class ThreadPool {

    /**
     * Данные о потоках (количество потоков) храним в листе {@link #threads}
     * Все задачи добавляем посредством экземпляра самописного класса
     * @see SimpleBlockingQueue
     */
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    /**
     * При создании объекта проверяем количество доступных ядер в системе и на каждое ядро создаем свой поток
     * Каждый вновьсозданный поток незамедлительно выполняет полезную работу
     */
    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (tasks.isEnoughQueueSize() || !Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    /**
     * Метод добавляет задачу
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Метод выставляет флаг прерывания
     */
    public void shutDown() {
        for (Thread thread: threads) {
            thread.interrupt();
        }
    }

    /**
     * Метод демонстрирует работу пула потоков.
     * Создаем пул потоков, переопределеяем метод run() (в данном случае просто выводим на печать старт начала работ)
     * Завершаем работу пула потоков. В результате все полученные ранее задачи в любом случае корректно завершаются
     */
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 35; i++) {
            try {
                pool.work(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("+++++++++++ pool.work starts");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pool.shutDown();
        System.out.println("------------ pool.work finished");
    }
}
