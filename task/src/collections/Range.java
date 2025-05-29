package collections;
public class Range<C extends Comparable> {

    private C low, high;
    private boolean isLowClosed, isHighClosed, isEmpty;

    private Range(C low, boolean isLowClosed, C high, boolean isHighClosed ) {
        this.low = low;
        this.isLowClosed = isLowClosed;
        this.high = high;
        this.isHighClosed = isHighClosed;
        this.isEmpty = conditionalSetEmpty();
    }

    public static <C extends Comparable<C>> Range<C> open(C a, C b) {
        checkNotNull(a, b); checkIfValidArguments(a, false , b, false );
        return new Range<>(a, false, b, false);
    }
    public static <C extends Comparable<C>> Range<C> closed(C a, C b) {
        checkNotNull(a, b); checkIfValidArguments(a, true , b, true );
        return new Range<>(a, true, b, true);
    }
    public static <C extends Comparable<C>> Range<C> openClosed(C a, C b) {
        checkNotNull(a, b); checkIfValidArguments(a, false , b, true );
        return new Range<>(a, false, b, true);
    }
    public static <C extends Comparable<C>> Range<C> closedOpen(C a, C b) {
        checkNotNull(a, b); checkIfValidArguments(a, true , b, false );
        return new Range<>(a, true, b, false);
    }
    public static <C extends Comparable<C>> Range<C> greaterThan(C a) {
        checkNotNull(a);
        return new Range<>(a, false, null, false);
    }
    public static <C extends Comparable<C>> Range<C> atLeast(C a) {
        checkNotNull(a);
        return new Range<>(a, true, null, false);
    }
    public static <C extends Comparable<C>> Range<C> lessThan(C a) {
        checkNotNull(a);
        return new Range<>(null, false, a, false);
    }
    public static <C extends Comparable<C>> Range<C> atMost(C a) {
        checkNotNull(a);
        return new Range<>(null, false, a, true);
    }
    public static Range all() {return new Range<>(null, false, null, false);}

    public boolean isEmpty() {return isEmpty;}

    public boolean contains(C value) {
        checkNotNull(value);
        return (low == null || (low.compareTo(value) < 0) || (low.compareTo(value) == 0 && isLowClosed))
            && (high == null || (high.compareTo(value) > 0) || (high.compareTo(value) == 0 && isHighClosed));
    }

    public boolean encloses(Range<C> newRange) {
        if(newRange.isEmpty && !isEmpty) return true;
        if(newRange.isEmpty && isEmpty) return false;
        return containsAllowNulls(newRange.low, true, newRange.isLowClosed) && containsAllowNulls(newRange.high, false, newRange.isHighClosed);
    }

    public Range<C> intersection(Range<C> connectedRange) {
        Range<C> intersection = new Range<>(low, isLowClosed, high, isHighClosed);
        intersection.isEmpty = true;

        if(isEmpty || connectedRange.isEmpty) return intersection;

        int modCount = 0;

        if(containsAllowNulls(connectedRange.low, true, connectedRange.isLowClosed)) {
            intersection.low = connectedRange.low; intersection.isLowClosed = connectedRange.isLowClosed;
            modCount++;
        }
        else if(connectedRange.containsAllowNulls(low, true, isLowClosed)) {
            intersection.low = low; intersection.isLowClosed = isLowClosed;
            modCount++;
        }

       if(containsAllowNulls(connectedRange.high, false, connectedRange.isHighClosed)) {
            intersection.high = connectedRange.high; intersection.isHighClosed = connectedRange.isHighClosed;
            modCount++;
        }
        else if(connectedRange.containsAllowNulls(high, false, isHighClosed)) {
            intersection.high = high; intersection.isHighClosed = isHighClosed;
            modCount++;
        }
        if(modCount == 2)
            intersection.isEmpty = false;
        return intersection;
    }

    public Range<C> span (Range<C> other){
        if(isEmpty && other.isEmpty) return other;
        if(isEmpty && !other.isEmpty) return other;
        if(!isEmpty && other.isEmpty) return new Range<>(low, isLowClosed, high, isHighClosed);

        C l, h;
        boolean isLClosed = false, isHClosed = false;
        if(low == null || other.low == null )
            l = null;
        else if (low.compareTo(other.low) <= 0) {
            l = low; isLClosed = isLowClosed;
            if(low.compareTo(other.low) == 0)
                if(other.isLowClosed)
                    isLClosed = true;
        } else {
            l = other.low; isLClosed = other.isLowClosed;
        }

        if(high == null || other.high == null )
            h = null;
        else if(high.compareTo(other.high) >= 0) {
            h = high; isHClosed = isHighClosed;
            if(high.compareTo(other.high) == 0)
                if(other.isHighClosed)
                    isHClosed = true;
        } else {
            h = other.high; isHClosed = other.isHighClosed;
        }
        return new Range<>(l, isLClosed, h, isHClosed);
    }

    @Override
    public String toString() {
        return isEmpty ? "EMPTY" : ((isLowClosed ? "[" : "(") + (low == null ? "-INF" : low) + ", " +
                (high == null ? "INF" : high) + (isHighClosed ? "]" : ")") );
    }

    private boolean conditionalSetEmpty() {
        if(low != null && low.equals(high))
             if(!isLowClosed || !isHighClosed)
                  return true;
         return false;
    }

    public boolean containsAllowNulls(C value, boolean... args) {
        if(value == null) {
            if (low == null && args[0]) return true;
            if (high == null && !args[0]) return true;
            return false;
        }
        if(args.length == 2) {
            if(args[0]) {
                if(value.equals(low) && args[1] == isLowClosed) return true;
            }else
                if(value.equals(high) && args[1] == isHighClosed) return true;
        }
        return contains(value);
    }

    @SafeVarargs
    private static <C> void checkNotNull(C... elements) {
        for (C e : elements) if (e == null) throw new NullPointerException();
    }

    private static <C extends Comparable<C>> void checkIfValidArguments(C low, boolean isLowClosed, C high, boolean isHighClosed) {
        if (low.compareTo(high) > 0) throw new IllegalArgumentException();
        if (low.compareTo(high) == 0) if( !isHighClosed && !isLowClosed ) throw new IllegalArgumentException();
    }
}
