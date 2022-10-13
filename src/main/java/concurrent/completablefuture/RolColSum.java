package concurrent.completablefuture;

import java.util.Arrays;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public String toString() {
            return "Sums{" + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums sums = new Sums(0, 0);
        Sums[] sumArr = new Sums[matrix.length];
        boolean isRow;
        for (int x = 0; x < matrix.length; x++) {
            int countX = sums.getColSum();
            int countY = sums.getRowSum();
            isRow = true;
            for (int y = 0; y < matrix[x].length; y++) {
                if (isRow) {
                    for (int row = 0; row < matrix.length; row++) {
                        countX += matrix[row][x];
                    }
                    isRow = false;
                }
                countY += matrix[x][y];
            }
            Sums sum = new Sums(countX, countY);
            sumArr[x] = sum;
        }
        System.out.println(Arrays.toString(sumArr));
        return sumArr;
    }
}
