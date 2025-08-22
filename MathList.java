import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MathList<MathNOde> implements Iterable<MathNode> {

    private int size;
    private MathNode head, tail;
    
    public MathList() {
        size = 0;
        head = tail = null;
    }

    public void add(MathNode node) {
        if (node == null) {
            throw new NullPointerException();
        }
        if (getSize() == 0) {
            head = tail = node;
            size++;
        } else if (getSize() == 1) {
            tail = node;
            tail.setPrevious(head);
            head.setNext(tail);
            size++;
        } else {
            tail.setNext(node);
            MathNode temp = tail;
            tail = node;
            tail.setPrevious(temp);
            size++;
        }
    }

    public MathNode remove(MathNode toRemove) {
        if (toRemove == null) {
            throw new NullPointerException();
        }
        MathNode node = find(toRemove);
        if (node != null) {
            if (getSize() == 1) {
                head = tail = null;
                size--;
            } else if (getSize() == 2) {
                if (head.isIdentical(node)) {
                    tail.setPrevious(null);
                    head = tail;
                    size--;
                } else {
                    head.setNext(null);
                    tail = head;
                    size--;
                }
            } else if (node.isIdentical(tail)) {
                MathNode newTail = tail.getPrevious();
                tail = newTail;
                tail.setNext(null);
                size--;
            } else if (node.isIdentical(head)) {
                MathNode newhead = head.getNext();
                head = newhead;
                head.setPrevious(null);
                size--;
            } else {
                MathNode prev = node.getPrevious();
                MathNode next = node.getNext();
                prev.setNext(next);
                next.setPrevious(prev);
                size--;
            }
        }
        return node;
    }

    public MathNode[] sortToPemdas() {
        MathNode[] pemdas = new MathNode[getSize()];
        int count = 0;
        LinkedList<MathNode> mddList = new LinkedList<>();
        LinkedList<MathNode> amList = new LinkedList<>();
        Iterator<MathNode> iter = this.iterator();
        while (iter.hasNext()) {
            MathNode node = iter.next();
            if (node.getPriority() == 1) {
                mddList.addLast(node);
            } else {
                amList.addLast(node);
            }
        }
        for (MathNode mn : mddList) {
            pemdas[count] = mn;
            count++;
        }
        for (MathNode mn : amList) {
            pemdas[count] = mn;
            count++;
        }

        return pemdas;
    }

    public MathNode find(MathNode toFind) {
        Iterator<MathNode> iter = this.iterator();
        while (iter.hasNext()) {
            MathNode node = iter.next();
            if (node.isIdentical(toFind)) {
                return node;
            }
        }

        return null;
    }

    public int getSize() {
        return size;
    }

    @Override
    public Iterator<MathNode> iterator() {
        return new MathNodeIterator();
    }

    public class MathNodeIterator implements Iterator<MathNode> {

        private MathNode current;
        private int index;

        private MathNodeIterator() {
            current = head;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < getSize();
        }

        @Override
        public MathNode next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MathNode item = current;
            current = current.getNext();
            index++;
            return item;
        }
    }
}
