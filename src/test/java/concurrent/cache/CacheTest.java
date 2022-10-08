package concurrent.cache;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        var cache = new Cache();
        Base base = new Base(1, 0);
        assertTrue(cache.add(base));
    }

    @Test
    void whenUpdate() {
        var cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Base newBase = new Base(1, 0);
        newBase.setName("Andrew");
        assertTrue(cache.update(newBase));
    }

    @Test
    void whenDelete() {
        var cache = new Cache();
        Base base = new Base(1, 0);
        cache.delete(base);
        assertNull(cache.getCache(base));
    }

    @Test
    void whenOptimisticException() {
        var cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Base newBase = new Base(1, 1);
        Exception exception = assertThrows(OptimisticException.class, () ->
                cache.update(newBase));
        assertEquals("Versions are not equal", exception.getMessage());
    }

}