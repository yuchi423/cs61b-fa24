package deque;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class Node {
        public T value;
        public Node prev;
        public Node next;

        public Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

    }

    private int items;
    private Node sentinel;


    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        items++;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        items++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>(items);
        Node nodePtr = sentinel.next;

        while (nodePtr != sentinel) {
            returnList.add(nodePtr.value);
            nodePtr = nodePtr.next;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return items == 0;
    }

    @Override
    public int size() {
        return items;
    }

    @Override
    public T removeFirst() {
        if(items == 0){
            return null;
        }

        T res = sentinel.next.value;

        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        items--;

        return res;
    }

    @Override
    public T removeLast() {
        if(items == 0){
            return null;
        }

        T res = sentinel.prev.value;

        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        items--;

        return res;
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < items) {
            Node nodePtr = sentinel.next;
            for (int i = 0; i < index; i++) {
                nodePtr = nodePtr.next;
            }
            return nodePtr.value;
        }

        return null;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= items) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node current, int remaining) {
        if (remaining == 0) {
            return current.value;
        }
        return getRecursiveHelper(current.next, remaining - 1);
    }

}
