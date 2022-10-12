package concurrent.pools;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MergeTest {

    private static final int CAPACITY = 1000;

    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        int[] array = new int[CAPACITY];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * CAPACITY);
        }
        long startSyncSort = System.currentTimeMillis();
        mergeSort.sort(array);
        System.out.println("Время выполнения MergeSort: "
                .concat(String.valueOf(System.currentTimeMillis() - startSyncSort)));
        ParallelMergeSort parallelMergeSort = new ParallelMergeSort(array, 0, array.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long startASyncSort = System.currentTimeMillis();
        forkJoinPool.invoke(parallelMergeSort);
        System.out.println("Время выполнения MergeSort: "
                .concat(String.valueOf(System.currentTimeMillis() - startASyncSort)));

        int[] smallArray = {14, 23, 52, 37, 6, 5, 3};
        int[] midArray = {14, 23, 52, 37, 6, 5, 3, 89, 72, 34, 86, 52, 43, 9, 68, 77};
        ParallelIndexSearch parallelASyncIndexSearch =
                new ParallelIndexSearch(smallArray, 0, smallArray.length - 1, 3);
        System.out.println(parallelASyncIndexSearch.compute());
        ParallelIndexSearch parallelSyncIndexSearch =
                new ParallelIndexSearch(midArray, 0, midArray.length - 1, 9);
        System.out.println(parallelSyncIndexSearch.compute());
    }
}
