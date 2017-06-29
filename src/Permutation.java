import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by Kostiantyn Kostin on 26.06.2017.
 */
public class Permutation {

    public static void main(String[] args) {
        Integer k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        Iterator<String> iterator = queue.iterator();
        while (k > 0) {
            StdOut.println(iterator.next());
            k--;
        }
    }
}