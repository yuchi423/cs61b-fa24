import java.util.Arrays;

public class UnionFind {

    private int size;
    private int[] p;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        p = new int[N];
        Arrays.fill(p, -1);
        size = N;
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if(v < 0 || v >= size) {
            throw new IllegalArgumentException("The index cannot be larger than the Size! ");
        }
        int root = find(v);
        return Math.abs(p[root]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if(v < 0 || v >= size) {
            throw new IllegalArgumentException("The index cannot be larger than the Size! ");
        }
        return p[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if(v < 0 || v >= size) {
            throw new IllegalArgumentException("The index cannot be larger than the Size! ");
        }
        if (p[v] < 0) return v;
        return p[v] = find(p[v]);
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);

        int weight1 = sizeOf(root1);
        int weight2 = sizeOf(root2);

        if(root1 == root2) return ;

        if (weight1 <= weight2) {
            p[root1] = root2;
            p[root2] -= weight1;
        } else {
            p[root2] = root1;
            p[root1] -= weight2;
        }

    }

}
