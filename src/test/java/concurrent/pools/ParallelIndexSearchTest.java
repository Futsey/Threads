package concurrent.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {

    @Test
    public void whenComputeASync() {
        Integer[] smallArray = {14, 23, 52, 37, 6, 5, 3};
        assertThat(ParallelIndexSearch.findIndex(smallArray, 14)).isEqualTo(0);
    }

    @Test
    public void whenComputeASyncLastEl() {
        Integer[] smallArray = {14, 23, 52, 37, 6, 5, 3};
        assertThat(ParallelIndexSearch.findIndex(smallArray, 3)).isEqualTo(6);
    }

    @Test
    public void whenComputeSync() {
        Integer[] midArray = {14, 23, 52, 37, 6, 5, 3, 89, 72, 34, 86, 52, 43, 9, 68, 77};
        assertThat(ParallelIndexSearch.findIndex(midArray, 9)).isEqualTo(13);
    }

    @Test
    public void whenNoElementFoundSync() {
        Integer[] smallArray = {14, 23, 52, 37, 6, 5, 3};
        assertThat(ParallelIndexSearch.findIndex(smallArray, 222)).isEqualTo(-1);
    }

    @Test
    public void whenNoElementFoundASync() {
        Integer[] midArray = {14, 23, 52, 37, 6, 5, 3, 89, 72, 34, 86, 52, 43, 9, 68, 77};
        assertThat(ParallelIndexSearch.findIndex(midArray, 1111)).isEqualTo(-1);
    }
}