package concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T el;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] array,  int from, int to, T el) {
        this.array = array;
        this.el = el;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return getEl();
        }
        int mid = (to - from) / 2;
        ParallelIndexSearch<T> leftPart = new ParallelIndexSearch<>(array, from, mid, el);
        ParallelIndexSearch<T> rightPart = new ParallelIndexSearch<>(array, mid + 1, to, el);
        leftPart.fork();
        rightPart.fork();
        Integer left = leftPart.join();
        Integer right = rightPart.join();
        return Math.max(left, right);
    }

    private int getEl() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(this.el)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> int findIndex(T[] array, T el) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, 0, array.length - 1, el));
    }
}
