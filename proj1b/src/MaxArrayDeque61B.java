import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private final Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }

        Iterator<T> it = iterator();
        T largest = it.next();

        while (it.hasNext()) {
            T candidate = it.next();

            if (c.compare(candidate, largest) > 0) {
                largest = candidate;
            }
        }

        return largest;
    }

}
