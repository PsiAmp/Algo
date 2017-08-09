
public class Binary<T extends Comparable> {

    public static final int ROOT_NODE = 1;
    private T[] data;
    private int size = 0;

    public Binary(T[] data) {
        this.data = (T[]) new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
    }

    public void add(T item) {
        // Validate item

        resize(size++);
        data[size] = item;

        // Reorder

    }

    public T getMax() {
        if (isEmpty()) return null;

        T maxItem = data[1];

        data[1] = data[size];
        // Prevent loitering
        data[size] = null;
        size--;

        if (!isEmpty()) {
            sink(1);
        }

        return maxItem;
    }

    private void sink(int i) {

    }

    private void swim(int i) {
        // Check current element is bigger than parent
        while (isLess(i/2, i)) {
            swap(i, i/2);
            i /= 2;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int size) {

    }

    private void swap(int i, int j) {
        T t = data[i];
        data[i] = data[j];
        data[j] = t;
    }

    private boolean isLess(int i, int j) {

        return data[i].compareTo(data[j]) < 0;
    }

}