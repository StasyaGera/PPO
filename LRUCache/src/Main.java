public class Main {
    public static void main(String[] args) {
        LRUCache<Integer, Integer> iiLruCache = new LRUCache<>(2);
        iiLruCache.put(1, 2);
        System.out.println(iiLruCache.get(1));

        iiLruCache = new LRUCache<>(2);
        iiLruCache.put(2, 3);
        iiLruCache.put(1, 5);
        iiLruCache.put(4, 5);
        iiLruCache.put(6, 7);
        System.out.println(iiLruCache.get(4));
        System.out.println(iiLruCache.get(1));
    }
}
