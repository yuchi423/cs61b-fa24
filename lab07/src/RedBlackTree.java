public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         *
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         *
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     *
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        if (node == null || node.left == null || node.right == null) return;

        boolean rootColor = node.isBlack;
        boolean childColor = node.left.isBlack;

        node.isBlack = childColor;
        node.left.isBlack = rootColor;
        node.right.isBlack = rootColor;

    }


    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     *
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> x = node.left;

        node.left = x.right;
        x.right = node;

        boolean oldColor = node.isBlack;
        node.isBlack = x.isBlack;
        x.isBlack = oldColor;

        return x;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     *
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> x = node.right;

        node.right = x.left;
        x.left = node;

        boolean oldColor = node.isBlack;
        node.isBlack = x.isBlack;
        x.isBlack = oldColor;

        return x;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     *
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     *
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     *
     * @param node
     * @param item
     * @return
     */

    /*
    CoT:
    1.如何确定op的时机？在树变得非法时。
    合法条件有：
        1.红孩子的父节点必须是黑色
        2.父节点不可以有两个红孩子
        3.如果父节点只有一个孩子，孩子必须在左侧
    2.怎么检测非法？
      2.1.插入时怎么知道插红还是黑？猜想：全部先插黑，红由flip产生--可这样的话，我无法在插入的同时判断合法性？猜想错误。按照2-3树的插入，每个节点似乎都是以红节点先存在
      2.2.红产生了，怎么知道他是否合法？遍历？
      2.3.怎么知道完成插入后，当前树是否平衡？猜想：或许不需要遍历判断，LLRB节点在插入过程中的性质自动保证与2-3Tree对应
    3.问题可能在于：拥有具体数值的节点，是如何插入LLRB的？或者再往前追溯，是如何插入2-3Tree的？
    4.解决：
        1.插入元素，若元素大于当前元素，且当前元素没有子节点，先插入再rotateLeft;rotateRight对称

     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        int cmp = item.compareTo(node.item);

        if (cmp > 0) {
            node.right = insert(node.right, item);
        } else if (cmp < 0) {
            node.left = insert(node.left, item);
        } else {
            return node;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }


}
