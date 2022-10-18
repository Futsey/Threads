package concurrent.completablefuture;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sumArr = new Sums[matrix.length];
        for (int x = 0; x < matrix.length; x++) {
            int countX = 0;
            int countY = 0;
            for (int y = 0; y < matrix.length; y++) {
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
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int index = i;
            CompletableFuture<Integer> sumRow = CompletableFuture.supplyAsync(() -> RolColSum.sumCol(matrix, index));
            CompletableFuture<Integer> sumCol = CompletableFuture.supplyAsync(() -> RolColSum.sumRow(matrix, index));
            sums[i] = new Sums(sumRow.get(), sumCol.get());
        }
        System.out.println(Arrays.toString(sums));
        return sums;
    }

    private static int sumCol(int[][] matrix, int rowIndex) {
        int rsl = 0;
        for (int col = 0; col < matrix.length; col++) {
            rsl += matrix[col][rowIndex];
        }
        return rsl;
    }

    private static int sumRow(int[][] matrix, int colIndex) {
        int rsl = 0;
        for (int row = 0; row < matrix.length; row++) {
            rsl += matrix[colIndex][row];
        }
        return rsl;
    }
}
