package collections;

public final class ImmutableCollection<E> {

    private final E[] elements;

    private ImmutableCollection(E... constructor) { this.elements = constructor; }

    public static <E> ImmutableCollection<E> of() { return new ImmutableCollection<>(); }

    public static <E> ImmutableCollection<E> of(E... elements) {
        for(E element : elements)
            if(element == null)
                throw new NullPointerException();

        return new ImmutableCollection<>(elements);
    }

    public boolean contains(E input) {
        for(E element : elements)
            if(input.equals(element))
                return true;

        return false;
    }

    public int size() { return this.elements.length; }

    public boolean isEmpty() { return this.elements.length == 0; }
}