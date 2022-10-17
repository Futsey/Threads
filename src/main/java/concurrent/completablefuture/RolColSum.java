package concurrent.completablefuture;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums sums = new Sums(0, 0);
        Sums[] sumArr = new Sums[matrix.length];
        for (int x = 0; x < matrix.length; x++) {
            int countX = sums.getColSum();
            int countY = sums.getRowSum();
            for (int y = 0; y < matrix[x].length; y++) {
                countX += matrix[y][x];
                countY += matrix[x][y];
            }
            Sums sum = new Sums(countX, countY);
            sumArr[x] = sum;
        }
        System.out.println(Arrays.toString(sumArr));
        return sumArr;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int length = matrix.length;
        Sums[] sums = new Sums[length];
        for (int i = 0; i < length; i++) {
            int row = i;
            CompletableFuture<Integer> sumRow = CompletableFuture.supplyAsync(() -> {
                int countX = 0;
                for (int x = 0; x < length; x++) {
                    countX += matrix[x][row];
                }
                return countX;
            });
            CompletableFuture<Integer> sumCol = CompletableFuture.supplyAsync(() -> {
                int countY = 0;
                for (int y = 0; y < length; y++) {
                    countY += matrix[row][y];
                }
                return countY;
            });
            sums[i] = new Sums(sumRow.get(), sumCol.get());
        }
        System.out.println(Arrays.toString(sums));
        return sums;
    }
}
