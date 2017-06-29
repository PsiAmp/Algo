import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Kostiantyn Kostin on 24.06.2017.
 */
public class Deque<E> implements Iterable<E> {

    protected Node first = null;
    protected Node last = null;
    protected int size = 0;

    protected class Node {
        Node prev;
        Node next;
        E item;

        public Node(Node prev, E item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    protected class DequeIterator implements Iterator {

        private Node node;

        public DequeIterator(Node node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();

            E item = node.item;
            node = node.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Construct an empty deque
     */
    public Deque() {}

    /**
     * Is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the number of items on the deque
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Add the item to the front
     * @param item
     */
    public void addFirst(E item) {
        if (item == null) throw new IllegalArgumentException();
        Node node = new Node(first, item, null);
        if (isEmpty()) {
            last = node;
        } else {
            first.next = node;
        }
        first = node;
        size++;
    }

    /**
     * add the item to the end
     * @param item
     */
    public void addLast(E item) {
        if (item == null) throw new IllegalArgumentException();
        Node node = new Node(null, item, last);
        if (isEmpty()) {
            first = node;
        } else {
            last.prev = node;
        }
        last = node;
        size++;
    }

    /**
     * Remove and return the item from the front
     * @return
     */
    public E removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        E item = first.item;
        first = first.prev;
        if (first != null) {
            first.next = null;
        }
        size--;
        return item;
    }

    /**
     * Remove and return the item from the end
     * @return
     */
    public E removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        E item = last.item;
        last = last.next;
        if (last != null) {
            last.prev = null;
        }
        size--;
        return item;
    }

    /**
     * Return an iterator over items in order from front to end
     * @return
     */
    public Iterator<E> iterator() {
        return new DequeIterator(last);
    }

    public static void main(String[] args) {
        Deque<Integer> integerDeque = new Deque<>();
        for (int i = 0; i < 10; i++) {
            if (i%2 == 0) {
                integerDeque.addFirst(i);
            } else {
                integerDeque.addLast(i);
            }
        }

//        int i = 0;
//        while (integerDeque.size > 0) {
//            i++;
//            if (i % 2 == 0) {
//                System.out.println(integerDeque.removeFirst());
//            } else {
//                System.out.println(integerDeque.removeLast());
//            }
//        }

        Iterator<Integer> iterator = integerDeque.iterator();
        while (iterator.hasNext()) {
            System.out.println("== " + iterator.next());
        }
    }

}