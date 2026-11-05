import edu.princeton.cs.algs4.BST;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
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

    private void collectKeys(BSTNode root, Set<K> st) {
        if (root == null) return;

        collectKeys(root.left, st);
        st.add(root.key);
        collectKeys(root.right, st);
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        collectKeys(root, keys);
        return keys;
    }

    // 删除节点，返回删除后的子树根节点
    private BSTNode delete(BSTNode node, K key){
        if(node == null) return null;

        int cmp = key.compareTo(node.key);

        if(cmp < 0){
            node.left = delete(node.left, key);
        } else if (cmp > 0){
            node.right = delete(node.right, key);
        } else{
            if(node.left == null) return node.right;
            if(node.right == null) return node.left;

            BSTNode successor = findMin(node.right);

            node.key = successor.key;
            node.val = successor.val;

            node.right = deleteMin(node.right);
        }

        return node;
    }

    private BSTNode findMin(BSTNode node){
        while(node.left != null){
            node = node.left;
        }
        return node;
    }

    private BSTNode deleteMin(BSTNode node){
        if(node.left == null) return node.right;

        node.left = deleteMin(node.left);
        return node;
    }

    @Override
    public V remove(K key) {
        BSTNode target = findCurRoot(root, key);
        if (target == null || key.compareTo(target.key) != 0) {
            return null;
        }

        V oldValue = target.val;

        root = delete(root, key);
        items--;

        return oldValue;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
