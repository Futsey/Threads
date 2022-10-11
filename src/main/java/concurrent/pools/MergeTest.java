package concurrent.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class MergeTest {

    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        int[] array = {8, 71, 9, 82, 35, 98, 24, 37, 68, 66, 52, 13, 87, 59, 3, 74, 65, 98, 13, 49, 6, 7, 8, 26, 35};
        System.out.println("Before sort: ".concat(Arrays.toString(array)));
        int[] syncSortArray = mergeSort.sort(array);
        System.out.println("After synchronized sort: ".concat(Arrays.toString(syncSortArray)));
        ParallelMergeSort parallelMergeSort = new ParallelMergeSort(array, 0, array.length - 1);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println("After fjp sort: ".concat(Arrays.toString(forkJoinPool.invoke(parallelMergeSort))));
    }
}
