
package data;

/**
 * Generic Data structure of a hashmap
 * @author brand
 * @param <K> the key of the hashmap
 * @param <V> the value of the hashmap
 */

import java.util.ArrayList;

public class HashMap<K, V> {
    
    //ths size of the hashmap's buckets
    private static final int INITIAL_CAPACITY = 16;
    //the values of the hashmap
    private ArrayList<SinglyLinkedList<Entry<K, V>>> buckets;
    //the size of the buckets
    private int size = 0;
    
    /**
     * Constructor for the generic hashmap data type
     */
    public HashMap() {
        //A new arraylist for the bucket entries
        this.buckets = new ArrayList<>();
        
        //Create new buckets for the hashmap
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets.add(new SinglyLinkedList<>());
        }
    }
    
    /**
     * Get the index of the hashmap's key
     * @param key the key of the hashmap
     * @return an int of where the key of the hashmap's index
     */
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode % buckets.size());
    }
    
    /**
     * Get the values associated with the key in the hashmap
     * @param key the key of the hashmap
     * @return a singly linked list of values associated with the key of the hashmap
     */
    private SinglyLinkedList<Entry<K, V>> getBucket(K key) {
        int bucketIndex = getBucketIndex(key);
        return buckets.get(bucketIndex);
    }
    
    /**
     * Find if there are values associated with the key of the hashmap
     * @param key the key of the hashmap
     * @param bucket the values associated with the key of the hashamp
     * @return an entry for the hashmap
     */
    private Entry<K, V> findEntry(K key, SinglyLinkedList<Entry<K, V>> bucket) {
        //loop through the entire bucket to find the entry, if it exists, return the entry, if not, return null
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        
        return null;
    }
    
    /**
     * Get the size of the bucket
     * @return the size as an int
     */
    public int size() {
        return buckets.size();
    }
    
    /**
     * Find out if the hashmap is empty or not
     * @return true/false
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Add an entry to the hashmaps bucket
     * @param key the key of the hashmap
     * @param value the value associated with the key
     */
    public void put(K key, V value) {
        //find the bucket that is storing the key of the hashmap
        SinglyLinkedList<Entry<K, V>> bucket = getBucket(key);
        //find the entry that is associated with the key and bucket of the hashmap
        Entry<K, V> existingEntry = findEntry(key, bucket);
        
        //Entry does exist, so add a another value to the key of the bucket
        if (existingEntry != null) {
            existingEntry.setValue(value);
        } else {
            //entry does not exist, create a new one and update the size of the buckets for the hashmap
            bucket.addLast(new Entry<>(key, value));
            size++;
        }
    }
    
    /**
     * Retrieve the bucket that is associated with the key of the hashmap
     * @param key the key associated with the bucket of the hashmap
     * @return the bucket associated with the key of the hashmap
     */
    public V get(K key) {
        //find the bucket that is storing the key of the hashmap
        SinglyLinkedList<Entry<K, V>> bucket = getBucket(key);
        //find the entry that is associated with the key and bucket of the hashmap
        Entry<K, V> entry = findEntry(key, bucket);
        
        //Entry does exist, so we return the value
        if (entry != null) {
            return entry.getValue();
        }
        
        //Entry does not exist
        return null;
    }
    
    /**
     * helper method to get the values of the hashmap
     * @return an iterable of the entries of the hashmap
     */
    private Iterable<Entry<K, V>> mapEntries() {
        //New arraylist that will store the entries of the hashamp
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        
        //outer loop that loops through the buckets of the hashmap
        for (SinglyLinkedList<Entry<K, V>> bucket : buckets) {
            //Inner loop that adds the buckets entry into the new hashamp
            for (Entry<K, V> entry : bucket) {
                entries.add(entry);
            }
        }
        
        //return the arraylist with all the entries of the hashmap
        return entries;
    }
    
    /**
     * Display the hashmap
     * @return a string of the hashmap
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        //loop through the entries of the mapentries helper method
        for(Entry<K, V> entry : mapEntries()) {
            //add the entries key and value to the stringbuilder
            sb.append(entry.getKey().toString() + "\n" +
                    entry.getValue().toString() + "\n");
        }
        
        //Turn the stringbuilder into a string
        return sb.toString();
    }
    
    /**
     * Nested class that helps to store the bucket entries
     * @param <K> key of the entry
     * @param <V> value of the entry
     */
    private static class Entry<K, V> {
        private K key;
        private V value;
        
        /**
         * The constructor of the nested entry class
         * @param key the key of the entry
         * @param value the value of the entry
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        /**
         * Get the key of the entry
         * @return the key of the entry
         */
        public K getKey() {
            return key;
        }
        
        /**
         * Get the value of the entry
         * @return the value of the entry
         */
        public V getValue() {
            return value;
        }
        
        /**
         * Set the value of the entry
         * @param value the value parameter updates the private value of the nested entry class
         */
        public void setValue(V value) {
            this.value = value;
        } 
    }
    
}
