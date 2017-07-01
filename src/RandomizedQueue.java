import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Kostiantyn Kostin on 24.06.2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Item[] items;

    private class RandomizedIterator implements Iterator<Item> {

        private int[] randIndexes;
        private int index = 0;

        public RandomizedIterator() {
            randIndexes = new int[size];
            for (int i = 0; i < randIndexes.length; i++) {
                randIndexes[i] = i;
            }
            StdRandom.shuffle(randIndexes);
        }

        @Override
        public boolean hasNext() {
            return index < randIndexes.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[randIndexes[index++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     *  Construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public int size() {
        return size;
    }

    /**
     * Is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    private void resize(int capacity) {
        Item[] resizedItems = (Item[]) new Object[capacity];

        for (int i = 0; i < size(); i++) {
            resizedItems[i] = items[i];
        }
        // Help GC
        items = null;
        items = resizedItems;

        // Alternative could be not supported by Algorithms course design specification
        // System.arraycopy(items, 0, resizedItems, 0, size());
    }

    /**
     * Add the item
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size() == items.length) {
            // double the size
            resize(items.length * 2);
        }
        items[size++] = item;
    }

    /**
     * Remove and return a random item
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(size());
        final Item item = items[randomIndex];

        for (int i = randomIndex + 1; i < size(); i++) {
            items[i-1] = items[i];
        }
        items[size()-1] = null;

        size--;

        // Half the size when 1/4th array is full to avoid threshing
        if (size() > 0 && size() == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    /**
     * Return (but do not remove) a random item
     * @return
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[StdRandom.uniform(size())];
    }

    /**
     * Return an independent iterator over items in random order
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();

        for (int i = 1; i < 1000; i++) {
            if (i % 8 == StdRandom.uniform(8)) {
                if (!randomizedQueue.isEmpty())
                    randomizedQueue.dequeue();
            } else {
                randomizedQueue.enqueue(StdRandom.uniform(1000));
            }
        }

        for (int i = 0; i < 1000; i++) {
            if (i % 8 == StdRandom.uniform(8)) {
                randomizedQueue.enqueue(StdRandom.uniform(1000));
            } else {
                if (!randomizedQueue.isEmpty())
                    randomizedQueue.dequeue();
            }
        }
        randomizedQueue.isEmpty();
    }
}
