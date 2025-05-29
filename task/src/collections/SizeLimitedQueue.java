package collections;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class SizeLimitedQueue<E> {

    private ArrayList<E> list;
    private final int maxSize;

    public SizeLimitedQueue(int limit) {
        if( limit <= 0)
            throw new IllegalArgumentException();

        this.list = new ArrayList<>(limit);
        this.maxSize = limit;
    }

    public void clear() { list.clear(); }

    public boolean isAtFullCapacity() { return list.size() == maxSize; }

    public boolean isEmpty() { return list.isEmpty(); }

    public int maxSize() { return maxSize; }

    public int size() { return list.size(); }

    public E[] toArray(E[] e) { return list.toArray(e); }

    public Object[] toArray() { return list.toArray(); }

    @Override
    public String toString() { return list.toString(); }

    public void add(E element) {
        if(element == null)
            throw new NullPointerException();

        if(list.size() == maxSize )
            list.remove(0);
        list.add(element);
    }

    public E peek() {
        if(list.isEmpty())
            return null;
        return list.get(0);
    }

    public E remove() {
        if(list.isEmpty())
           throw new NoSuchElementException();
        return list.remove(0);
    }
}
