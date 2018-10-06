import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class LRUCacheTest {
    private LRUCache<Integer, Integer> iiLruCache;
    private int capacity = 10;

    @BeforeEach
    void setUp() {
        iiLruCache = new LRUCache<>(capacity);
    }

    @Test
    void put() {
        for (int i = 0; i < capacity + (capacity / 2); i++) {
            iiLruCache.put(i, i);
            assertEquals(Math.min(i + 1, capacity), iiLruCache.size());
        }

        for (int i = capacity / 2 + 1; i < capacity + capacity / 2; i++) {
            assertEquals(i, iiLruCache.get(i).intValue());
        }

        for (int i = 0; i < capacity / 2; i++) {
            try {
                iiLruCache.get(i);
                fail("Did not overwrite elements");
            } catch (AssertionError ignored) {
            }
        }
    }

    @Test
    void get() {
        for (int i = 0; i < capacity; i++) {
            iiLruCache.put(i, i);
        }
        for (int i = 0; i < capacity / 2; i++) {
            iiLruCache.get(i);
        }
        for (int i = capacity; i < capacity + (capacity / 2); i++) {
            iiLruCache.put(i, i);
        }

        for (int i = 0; i < capacity / 2; i++) {
            try {
                iiLruCache.get(i);
            } catch (AssertionError error) {
                fail("Did not put accessed elements on top");
            }
        }
    }

    @Test
    void delete() {
        for (int i = 0; i < capacity; i++) {
            iiLruCache.put(i, i);
        }

        for (int i = 0; i < capacity; i++) {
            iiLruCache.delete(i);
        }

        for (int i = 0; i < capacity; i++) {
            try {
                iiLruCache.get(i);
                fail("Deleted elements still accessible");
            } catch (AssertionError ignored) {
            }
        }
    }
}