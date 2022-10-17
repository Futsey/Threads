package concurrent.completablefuture;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class RolColSumTest {

    @Test
    void whenSumSuccessfull() {
        int[][] originArr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum rolColSum = new RolColSum();
        String origin = Arrays.toString(rolColSum.sum(originArr));
        String expected = "[Sums{rowSum=12, colSum=6}, Sums{rowSum=15, colSum=15}, Sums{rowSum=18, colSum=24}]";
        assertThat(origin, is(expected));
    }

    @Test
    void whenAsyncSumSuccessfull() throws ExecutionException, InterruptedException {
        int[][] originArr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        String origin = Arrays.toString(RolColSum.asyncSum(originArr));
        String expected = "[Sums{rowSum=12, colSum=6}, Sums{rowSum=15, colSum=15}, Sums{rowSum=18, colSum=24}]";
        assertThat(origin, is(expected));
    }
}