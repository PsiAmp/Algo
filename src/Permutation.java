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
            if (k > 0) {
                queue.enqueue(s);
                k--;
            }
        }

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }
}