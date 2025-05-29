package collections;

import java.util.*;

public class BiMap<K, V> {

    private HashMap<K, V> map;

    public BiMap() { this.map = new HashMap<>(); }
    private BiMap(HashMap<K, V> inverseMap) { this.map = inverseMap; }

    @Override
    public String toString() { return this.map.toString(); }

    public Set<V> values() { return new HashSet<>(this.map.values()); }

    public V put(K key, V value) {
        if(this.map.containsKey(key) || this.map.containsValue(value))
            throw new IllegalArgumentException();

        this.map.put(key, value);
        return value;
    }

    public void putAll (Map<K, V> map) {
        for(var entry : map.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    public V forcePut(K key, V value) {

        Iterator<Map.Entry<K, V>> iterator = this.map.entrySet().iterator();

        while(iterator.hasNext())
            if(iterator.next().getValue().equals(value))
                iterator.remove();

        //this.map.entrySet().removeIf(kvEntry -> kvEntry.getValue().equals(value));

        this.map.put(key, value);
        return value;
    }

    public BiMap<K, V> inverse(){
        HashMap<K, V> inverse = new HashMap<>();

        for (var entry : this.map.entrySet())
            inverse.put((K)entry.getValue(), (V)entry.getKey());

        return new BiMap<K, V>(inverse);
    }
}
