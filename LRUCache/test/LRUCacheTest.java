import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            assertEquals(Math.min(i + 1, capacity), iiLruCache.size(),
                    iiLruCache.size() > capacity
                            ? "Cache size larger than capacity"
                            : "Cache size is not equal to amount of elements held");
        }

        for (int i = 0; i < capacity / 2; i++) {
            int finalI = i;
            assertThrows(AssertionError.class, () -> iiLruCache.get(finalI),
                    "Did not overwrite element " + finalI);
        }

        for (int i = capacity / 2; i < capacity + (capacity / 2); i++) {
            assertEquals(i, iiLruCache.get(i).intValue(),
                    "Wrong value for key " + i);
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
                fail("Did not put accessed element " + i + " on top");
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
            assertEquals(capacity - i - 1, iiLruCache.size(),
                    "Size does not change after delete");
        }

        for (int i = 0; i < capacity; i++) {
            int finalI = i;
            assertThrows(AssertionError.class, () -> iiLruCache.get(finalI),
                    "Deleted element " + finalI + " still accessible");
        }
    }
}