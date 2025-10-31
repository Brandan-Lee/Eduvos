
package data;

import java.util.ArrayList;

public class HashMap<K, V> {
    
    private static final int INITIAL_CAPACITY = 16;
    private ArrayList<SinglyLinkedList<Entry<K, V>>> buckets;
    private int size = 0;
    
    public HashMap() {
        this.buckets = new ArrayList<>();
        
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets.add(new SinglyLinkedList<>());
        }
    }
    
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode % buckets.size());
    }
    
    public int size() {
        return buckets.size();
    }
    
    public boolean isEmpty() {
        return buckets.size() == 0;
    }
    
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        SinglyLinkedList<Entry<K, V>> bucket = buckets.get(bucketIndex);
        
        for (Entry<K, V> entry: bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        
        bucket.addLast(new Entry<>(key, value));
        size++;
    }
    
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        SinglyLinkedList<Entry<K, V>> bucket = buckets.get(bucketIndex);
        
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        
        return null;
    }
    
    private Iterable<Entry<K, V>> mapEntries() {
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        
        for (SinglyLinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                entries.add(entry);
            }
        }
        
        return entries;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(Entry<K, V> entry : mapEntries()) {
            sb.append(entry.getKey().toString() + "\n" +
                    entry.getValue().toString() + "\n");
        }
        
        return sb.toString();
    }
    
    private static class Entry<K, V> {
        private K key;
        private V value;
        
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public void setKey(K key) {
            this.key = key;
        }
        
        public V getValue() {
            return value;
        }
        
        public void setValue(V value) {
            this.value = value;
        } 
    }
    
}
