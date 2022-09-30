package concurrent.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> someArrList = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(someArrList);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void whenGet() throws InterruptedException {
        List<Integer> someArrList = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(someArrList);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(25));
        first.start();
        second.start();
        first.join();
        second.join();
        int rsl = 25;
        assertThat(rsl, is(list.get(1)));
    }
}