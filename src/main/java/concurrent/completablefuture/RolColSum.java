package concurrent.completablefuture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sumArr = new Sums[matrix.length];
        for (int x = 0; x < matrix.length; x++) {
            sumArr[x] = lineSum(matrix, x);
        }
        System.out.println(Arrays.toString(sumArr));
        return sumArr;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> data = new HashMap<>();
        Sums[] sumsArray = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            data.put(i, getSums(matrix, i));
        }
        for (Integer key : data.keySet()) {
            sumsArray[key] = data.get(key).get();
        }
        return sumsArray;
    }

    public static CompletableFuture<Sums> getSums(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(
                () -> lineSum(matrix, index)
        );
    }

    private static Sums lineSum(int[][] matrix, int index) {
            int countX = 0;
            int countY = 0;
            for (int y = 0; y < matrix.length; y++) {
                countX += matrix[y][index];
                countY += matrix[index][y];
            }
            return new Sums(countX, countY);
    }
}