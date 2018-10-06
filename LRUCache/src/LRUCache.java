import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int capacity;
    private Node head;
    private Node tail;
    private Map<K, Node> keyToValueMap;

    LRUCache(int capacity) {
        assert capacity > 0;

        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        keyToValueMap = new HashMap<>();
    }

    public void put(@NotNull K key, V value) {
        assert key != null;

        if (keyToValueMap.containsKey(key)) {
            keyToValueMap.get(key).update(value);
            return;
        }

        if (this.size() == capacity) {
            delete(tail.prev.key);
        }

        keyToValueMap.put(key, new Node(key, value));
    }

    public V get(@NotNull K key) {
        assert key != null;
        assert keyToValueMap.containsKey(key);

        Node victim = keyToValueMap.get(key);
        victim.touch();
        return victim.value;
    }

    public void delete(@NotNull K key) {
        assert key != null;
        assert keyToValueMap.containsKey(key);

        keyToValueMap.get(key).remove();
        keyToValueMap.remove(key);
    }

    public int size() {
        return keyToValueMap.size();
    }

    private class Node {
        private final K key;
        private V value;
        private Node prev = null;
        private Node next = null;

        private Node() {
            key = null;
            value = null;
        }

        Node(@NotNull K key, V value) {
            assert key != null;

            this.key = key;
            this.value = value;
            placeOnTop(this);
        }

        void update(V value) {
            this.remove();
            this.value = value;
            placeOnTop(this);
        }

        void touch() {
            this.remove();
            placeOnTop(this);
        }

        void remove() {
            prev.next = this.next;
            next.prev = this.prev;
        }

        private void placeOnTop(Node node) {
            Node second = head.next;
            node.next = second;
            second.prev = node;
            node.prev = head;
            head.next = node;
        }
    }
}
