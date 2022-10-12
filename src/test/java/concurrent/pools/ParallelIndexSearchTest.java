package concurrent.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {

    Integer[] smallArray = {14, 23, 52, 37, 6, 5, 3};
    Integer[] midArray = {14, 23, 52, 37, 6, 5, 3, 89, 72, 34, 86, 52, 43, 9, 68, 77};

    @Test
    public void whenComputeASync() {
        assertThat(ParallelIndexSearch.findIndex(smallArray, 14)).isEqualTo(0);
    }

    @Test
    public void whenComputeSync() {
        assertThat(ParallelIndexSearch.findIndex(midArray, 9)).isEqualTo(13);
    }

    @Test
    public void whenNoElementFoundSync() {
        assertThat(ParallelIndexSearch.findIndex(smallArray, 222)).isEqualTo(-1);
    }

    @Test
    public void whenNoElementFoundASync() {
        assertThat(ParallelIndexSearch.findIndex(midArray, 1111)).isEqualTo(-1);
    }
}