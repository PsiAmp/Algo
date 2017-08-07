public class Selection {

    public static void main(String[] args) {
        //Character[] a = {'b', 'a'};
        Character[] a = {'k', 'c', 'a', 'n', 't', 'b', 'x'};
        //partition(a, 0, a.length-1);
        qsort(a, 0, a.length-1);

        for (Comparable element : a) {
            System.out.print(element);
        }
    }

    public static void qsort(Comparable[] a, int low, int high) {
        if (low >= high) return;

        int j = partition(a, low, high);

        qsort(a, low, j-1);
        qsort(a, j+1, high);
    }

    public static int partition(Comparable[] a, int low, int high) {
        int i = low;
        int j = high+1;

        while (true) {
            while (a[++i].compareTo(a[low]) < 0 && i != high);
            while (a[low].compareTo(a[--j]) < 0 && j != low);

            if (i >= j) {
                swap(a, low, j);
                return j;
            }
            swap(a, i, j);
        }
    }

    public static Comparable[] swap(Comparable[] a, int x, int y) {
        Comparable t = a[x];
        a[x] = a[y];
        a[y] = t;
        return a;
    }

}
