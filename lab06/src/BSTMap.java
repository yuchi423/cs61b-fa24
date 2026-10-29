import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V val;

        BSTNode left;
        BSTNode right;

        BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private BSTNode root;
    private int items;

    BSTMap(BSTNode root) {
        this.root = root;
        this.items = 0;
    }

    BSTMap() {
        this.root = null;
        this.items = 0;
    }

    private BSTNode findCurRoot(BSTNode root, K key) {
        if (root == null) return null;

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            return root;
        } else if (cmp < 0) {
            if (root.left == null) {
                return root;
            }
            return findCurRoot(root.left, key);
        } else {
            if (root.right == null) {
                return root;
            }
            return findCurRoot(root.right, key);
        }
    }


    @Override
    public void put(K key, V value) {
        BSTNode parent = findCurRoot(root, key);
        BSTNode node = new BSTNode(key, value);

        if (parent == null) {
            root = node;
            items++;
            return;
        }

        int cmp = key.compareTo(parent.key);

        if (cmp == 0) {
            parent.val = value;
        } else if (cmp < 0) {
            parent.left = node;
            items++;
        } else {
            parent.right = node;
            items++;
        }
    }

    @Override
    public V get(K key) {
        BSTNode parent = findCurRoot(root, key);
        if (parent == null) return null;

        int cmp = key.compareTo(parent.key);

        if (cmp == 0) return parent.val;

        return null;
    }

    @Override
    public boolean containsKey(K key) {

        BSTNode parent = findCurRoot(root, key);
        if (parent == null) return false;

        return key.compareTo(parent.key) == 0;
    }

    @Override
    public int size() {
        return items;
    }

    @Override
    public void clear() {
        root = null;
        items = 0;
    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
