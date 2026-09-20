import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private T[] items;

    private int nextFirst;
    private int nextLast;
    private int size;


    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        this.size = 0;
        this.nextFirst = 4;
        this.nextLast = 5;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resizeUp();
        }

        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resizeUp();
        }

        items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size++;
    }

    private void resizeUp() {
        T[] temp = (T[]) new Object[size * 2];

        for (int i = 0; i < size; i++) {
            int oldIdx = Math.floorMod(nextFirst + 1 + i, size);
            temp[i] = items[oldIdx];
        }

        items = temp;
        nextFirst = size * 2 - 1;
        nextLast = size;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (items.length >= 16 && 4 * size <= items.length) {
            resizeDown();
        }
        if (size == 0) {
            return null;
        }

        int firstIdx = Math.floorMod(nextFirst + 1, items.length);

        T result = items[firstIdx];
        items[firstIdx] = null;
        size--;
        nextFirst = firstIdx;

        return result;
    }

    @Override
    public T removeLast() {
        if (items.length >= 16 && 4 * size <= items.length) {
            resizeDown();
        }
        if (size == 0) {
            return null;
        }

        int lastIdx = Math.floorMod(nextLast - 1, items.length);

        T result = items[lastIdx];
        items[lastIdx] = null;
        size--;
        nextLast = lastIdx;

        return result;
    }

    private void resizeDown() {
        int n = items.length;
        ;
        T[] temp = (T[]) new Object[n / 2];

        for (int i = 0; i < size; i++) {
            int oldIdx = Math.floorMod(nextFirst + 1 + i, n);
            temp[i] = items[oldIdx];
        }

        items = temp;
        nextFirst = n / 2 - 1;
        nextLast = size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        int realIdx = Math.floorMod(nextFirst + 1 + index, items.length);
        return items[realIdx];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement this method");
    }
}
