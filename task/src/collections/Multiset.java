package collections;

import java.util.*;
public class Multiset<E> {

    private HashMap<E, Integer> map;

    public Multiset() { this.map = new HashMap<>(); }

    public boolean contains(E element) { return this.map.containsKey(element); }

    public int count(E element) { return this.map.getOrDefault(element, 0); }

    public Set<E> elementSet() { return this.map.keySet(); }

    public void add (E element) {
        if(this.map.containsKey(element) && this.map.get(element) + 1 <= Integer.MAX_VALUE)
            this.map.replace(element, this.map.get(element) + 1);
        else
            this.map.put(element, 1);
    }

    public void add (E element, int occurrences) {
        if(occurrences <= 0)
            return;

        if(this.map.containsKey(element) && this.map.get(element) + occurrences <= Integer.MAX_VALUE )
            this.map.replace(element, this.map.get(element) + occurrences);
        else
            this.map.put(element, occurrences);
    }

    public void remove(E element) {
        if(this.map.containsKey(element)) {
            if(this.map.get(element) > 1)
                this.map.replace(element, this.map.get(element) - 1);
            else
                this.map.remove(element);
        }
    }

    public void remove(E element, int occurrences) {
        if(occurrences <= 0)
            return;

        if(this.map.containsKey(element)) {
            if(occurrences >= this.map.get(element))
                this.map.remove(element);
            else
                this.map.replace(element, this.map.get(element) - occurrences);
        }
    }

    public void setCount(E element, int occurrences) {
        if (occurrences == 0)
            map.remove(element);

        if (occurrences > 0)
            map.replace(element, occurrences);
    }

    public void setCount(E element, int oldCount, int newCount) {
        if (Integer.valueOf(oldCount).equals(map.get(element)))
            setCount(element, newCount); // calls the other method
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");

        Iterator<Map.Entry<E, Integer>> itr = this.map.entrySet().iterator();
        for(var entry : this.map.entrySet()) {
            itr.next();
            for(int i = 0; i < entry.getValue(); i++) {
                str.append(entry.getKey());
                if(i != entry.getValue() -1)
                    str.append(", ");
            }
            if(itr.hasNext())
                str.append(", ");
        }
        str.append("]");

        return str.toString();
    }
}
