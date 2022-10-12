package concurrent.pools;

import java.util.NoSuchElementException;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch extends RecursiveTask<Integer> {

    private final int[] array;
    private final int el;
    private final int from;
    private final int to;

    public ParallelIndexSearch(int[] array,  int from, int to, int el) {
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
        ParallelIndexSearch leftPart = new ParallelIndexSearch(array, from, mid, el);
        ParallelIndexSearch rightPart = new ParallelIndexSearch(array, mid + 1, to, el);
        leftPart.fork();
        rightPart.fork();
        int left = leftPart.join();
        int right = rightPart.join();
        return Math.max(left, right);
    }

    private synchronized int getEl() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == this.el) {
                return i;
            }
        }
        throw new NoSuchElementException("No such element was found. You searched: ".concat(String.valueOf(el)));
    }
}
