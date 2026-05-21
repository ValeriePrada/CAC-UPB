package co.edu.upb.proyecto.datastructures.app.linkedlist.circular;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import co.edu.upb.proyecto.datastructures.app.linkedlist.node.doubly.DoublyLinkedNode;
import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;
import co.edu.upb.proyecto.datastructures.model.list.AbstractList;
import co.edu.upb.proyecto.datastructures.model.list.List;

public class CircularDoublyList<E> extends AbstractList<E> {

    private transient DoublyLinkedNode<E> head;
    private transient DoublyLinkedNode<E> tail;
    private transient DoublyLinkedNode<E> inode;

    public CircularDoublyList() {
        head = tail = null;
        size = 0;
    }

    public CircularDoublyList(E element) {
        DoublyLinkedNode<E> node = new DoublyLinkedNode<>(element);
        head = tail = node;
        head.setPrev(tail);
        tail.setNext(head);
        size = 1;
    }

    public boolean add(E element) {
        if (element == null) {
            return false;
        }

        DoublyLinkedNode<E> node = new DoublyLinkedNode<>(element);

        if (head == null) {
            head = tail = node;
            head.setPrev(tail);
            tail.setNext(head);
        } else {
            tail.setNext(node);
            node.setPrev(tail);
            node.setNext(head);
            head.setPrev(node);
            tail = node;
        }

        size++;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        return "CircularDoublyList [head=" + head.get() + ", tail=" + tail.get() + 
        ", size=" + size + "]";
    }

    @Override
    public boolean add(E[] array) {
        if (array == null || array.length == 0) {
        return false;
    }

    boolean added = false;

    for (int i = 0; i < array.length; i++) {
        if (add(array[i])) {
            added = true;
        }
    }

    return added;
    }

    @Override
    public boolean add(Collection<E> collection) {
        if (collection == null || collection.size() == 0) {
        return false;
    }

    boolean added = false;
    Iterator<E> it = collection.iterator();

    while (it.hasNext()) {
        if (add(it.next())) {
            added = true;
        }
    }

    return added;
    }

    @Override
    public boolean addFirst(E element) {
        if (element == null) {
            return false;
        }

        DoublyLinkedNode<E> node = new DoublyLinkedNode<>(element);

        if (head == null) {
            head = tail = node;
            head.setPrev(tail);
            tail.setNext(head);
        } else {
            node.setNext(head);
            node.setPrev(tail);
            head.setPrev(node);
            tail.setNext(node);
            head = node;
        }

        size++;
        return true;

    }

    @Override
    public boolean addFirst(E[] array) {
        if (array == null || array.length == 0) {
        return false;
    }

        boolean added = false;

        for (int i = array.length - 1; i >= 0; i--) {
            if (addFirst(array[i])) {
                added = true;
            }
        }

        return added;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addFirst(Collection<E> collection) {
        if (collection == null || collection.size() == 0) {
            return false;
        }

        int n = collection.size();
        E[] elements = (E[]) new Object[n];
        int i = 0;
        Iterator<E> it = collection.iterator();

        while (it.hasNext()) {
            elements[i++] = it.next();
        }

        boolean added = false;

        for (i = n - 1; i >= 0; i--) {
            if (addFirst(elements[i])) {
                added = true;
            }
        }

        return added;
    }

    @Override
    public E peek() {
        if (head != null) {
            return head.get();
        }
        return null;
    }

    @Override
    public E peekLast() {
        if (tail != null) {
            return tail.get();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] peekArray(int n) {
        if (n <= 0 || size == 0) {
            return null;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        E[] array = (E[]) new Object[limit];
        DoublyLinkedNode<E> node = head;

        for (int i = 0; i < limit; i++) {
            array[i] = node.get();
            node = node.getNext();
        }

        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] peekLastArray(int n) {

        if (n <= 0 || size == 0) {
            return null;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        E[] array = (E[]) new Object[limit];
        DoublyLinkedNode<E> node = tail;

        for (int i = limit - 1; i >= 0; i--) {
            array[i] = node.get();
            node = node.getPrev();
        }

        return array;
    }

    @Override
    public List<E> peekCollection(int n) {

        CircularDoublyList<E> result = new CircularDoublyList<>();

        if (n <= 0 || size == 0) {
            return result;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        DoublyLinkedNode<E> node = head;
        int count = 0;

        while (count < limit) {
            result.add(node.get());
            node = node.getNext();
            count++;
        }

        return result;
    }

    @Override
    public List<E> peekLastCollection(int n) {
        CircularDoublyList<E> result = new CircularDoublyList<>();

        if (n <= 0 || size == 0) {
            return result;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        DoublyLinkedNode<E> node = tail;
        for (int i = 0; i < limit; i++) {
            result.addFirst(node.get());
            node = node.getPrev();
        }

        return result;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        E element = head.get();

        if (size == 1) {
            head = tail = null;
        } else {
            head = head.getNext();
            head.setPrev(tail);
            tail.setNext(head);
        }

        size--;
        return element;
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }

        E element = tail.get();

        if (size == 1) {
            head = tail = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(head);
            head.setPrev(tail);
        }

        size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] pollArray(int n) {
        if (n <= 0 || size == 0) {
            return null;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        E[] array = (E[]) new Object[limit];

        for (int i = 0; i < limit; i++) {
            array[i] = poll();
        }

        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] pollLastArray(int n) {
        if (n <= 0 || size == 0) {
            return null;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        E[] array = (E[]) new Object[limit];

        for (int i = limit - 1; i >= 0; i--) {
            array[i] = pollLast();
        }

        return array;
    }

    @Override
    public List<E> pollCollection(int n) {
        CircularDoublyList<E> result = new CircularDoublyList<>();

        if (n <= 0 || size == 0) {
            return result;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        for (int i = 0; i < limit; i++) {
            result.add(poll());
        }

        return result;
    }

    @Override
    public List<E> pollLastCollection(int n) {
        CircularDoublyList<E> result = new CircularDoublyList<>();

        if (n <= 0 || size == 0) {
            return result;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        for (int i = 0; i < limit; i++) {
            result.addFirst(pollLast());
        }

        return result;
    }

    @Override
    public boolean remove(E element) {

        if (size == 0 || element == null) {
            return false;
        }

        inode = head;
        int count = 0;

        while (count < size) {
            if (inode.get().equals(element)) {

                if (size == 1) {
                    head = tail = null;
                } else if (inode == head) {
                    head = head.getNext();
                    head.setPrev(tail);
                    tail.setNext(head);
                } else if (inode == tail) {
                    tail = tail.getPrev();
                    tail.setNext(head);
                    head.setPrev(tail);
                } else {
                    inode.getPrev().setNext(inode.getNext());
                    inode.getNext().setPrev(inode.getPrev());
                }

                size--;
                return true;
            }

            inode = inode.getNext();
            count++;
        }

        return false;

    }

    @Override
    public boolean remove(E[] array) {
        if (array == null || array.length == 0 || size == 0) {
            return false;
        }

        boolean removedAny = false;

        for (int i = 0; i < array.length; i++) {
            while (remove(array[i])) {
                removedAny = true;
            }
        }

        return removedAny;
    }

    @Override
    public boolean remove(Collection<E> collection) {
        if (collection == null || collection.size() == 0 || size == 0) {
            return false;
        }

        boolean removedAny = false;
        Iterator<E> it = collection.iterator();

        while (it.hasNext()) {
            E element = it.next();
            while (remove(element)) {
                removedAny = true;
            }
        }

        return removedAny;
    }

    @Override
    public boolean remove(Predicate<E> filter) {
        if (filter == null || size == 0) {
            return false;
        }

        boolean removedAny = false;
        int count = 0;
        inode = head;

        while (count < size) {
            DoublyLinkedNode<E> nextNode = inode.getNext();

            if (filter.test(inode.get())) {
                remove(inode.get());
                removedAny = true;
            }

            inode = nextNode;
            count++;
        }

        return removedAny;
    }

    @Override
    public boolean replace(E element, E newElement, Predicate<E> comparator) {
        if (element == null || newElement == null || comparator == null || size == 0) {
            return false;
        }

        boolean replacedAny = false;
        int count = 0;
        inode = head;

        while (count < size) {
            if (inode.get().equals(element) && comparator.test(inode.get())) {
                inode.set(newElement);
                replacedAny = true;
            }

            inode = inode.getNext();
            count++;
        }

        return replacedAny;
    }

    @Override
    public boolean replace(E[] array, E[] newArray, Predicate<E> comparator) {
        if (array == null || newArray == null || array.length != newArray.length || comparator == null || size == 0) {
            return false;
        }

        boolean replacedAny = false;

        for (int i = 0; i < array.length; i++) {
            if (replace(array[i], newArray[i], comparator)) {
                replacedAny = true;
            }
        }

        return replacedAny;
    }

    @Override
    public boolean replace(Collection<E> collection, Collection<E> newCollection, Predicate<E> comparator) {
        if (collection == null || newCollection == null || collection.size() != 
            newCollection.size() || comparator == null || size == 0) {
            return false;
        }

        boolean replacedAny = false;

        Iterator<E> itOld = collection.iterator();
        Iterator<E> itNew = newCollection.iterator();

        while (itOld.hasNext() && itNew.hasNext()) {
            if (replace(itOld.next(), itNew.next(), comparator)) {
                replacedAny = true;
            }
        }

        return replacedAny;
    }

    @Override
    public boolean retain(E[] array) {
        if (size == 0) {
            return false;
        }

        if (array == null || array.length == 0) {
            clear();
            return true;
        }

        boolean modified = false;
        int count = 0;
        inode = head;

        while (count < size) {
            boolean found = false;
            for (int i = 0; i < array.length; i++) {
                if (inode.get().equals(array[i])) {
                    found = true;
                    break;
                }
            }

            DoublyLinkedNode<E> nextNode = inode.getNext();

            if (!found) {
                remove(inode.get());
                modified = true;
            }

            inode = nextNode;
            count++;
        }

        return modified;
    }

    @Override
    public boolean retain(Collection<E> collection) {
        if (size == 0) return false;
    if (collection == null || collection.size() == 0) {
        clear();
        return true;
    }

    boolean modified = false;
    int checked = 0;
    int originalSize = size;

    while (checked < originalSize) {
        E element = peek();
        if (!collection.contains(element)) {
            poll(); 
            modified = true;
        } else {
            add(poll()); 
        }
        checked++;
    }

    return modified;
    }

    @Override
    public boolean set(E index, E element) {
        if (index == null || element == null || size == 0) {
            return false;
        }

        inode = head;
        int count = 0;

        while (count < size) {
            if (inode.get().equals(index)) {
                inode.set(element);
                return true;
            }
            inode = inode.getNext();
            count++;
        }

        return false;
    }

    @Override
    public boolean sort(ToIntFunction<E> toInt) {

        if (toInt == null || size <= 1) {
            return true;
        }

        for (int i = 0; i < size - 1; i++) {
            inode = head;
            for (int j = 0; j < size - 1; j++) {
                DoublyLinkedNode<E> nextNode = inode.getNext();
                if (toInt.applyAsInt(inode.get()) > toInt.applyAsInt(nextNode.get())) {
                    E temp = inode.get();
                    inode.set(nextNode.get());
                    nextNode.set(temp);
                }
                inode = inode.getNext();
            }
        }

        return true;
    }

    @Override
    public List<E> subList(E from, E to) {
        CircularDoublyList<E> result = new CircularDoublyList<>();

        if (from == null || to == null || size == 0) {
            return result;
        }

        boolean inRange = false;
        int count = 0;
        inode = head;

        while (count < size) {
            if (inode.get().equals(from)) {
                inRange = true;
            }

            if (inRange) {
                result.add(inode.get());
            }

            if (inode.get().equals(to)) {
                break;
            }

            inode = inode.getNext();
            count++;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        if (size == 0) {
        return (E[]) new Object[0];
        }

        E[] array = (E[]) new Object[size];
        int count = 0;
        inode = head;

        while (count < size) {
            array[count] = inode.get();
            inode = inode.getNext();
            count++;
        }

        return array;
    }

    @Override
    public boolean clear() {
        head = tail = inode = null;
        size = 0;
        return true;
    }

    @Override
    public boolean contains(E element) {
        if (element == null || size == 0) {
            return false;
        }

        inode = head;
        int count = 0;

        while (count < size) {
            if (inode.get().equals(element)) {
                return true;
            }
            inode = inode.getNext();
            count++;
        }

        return false;
    }

    @Override
    public boolean contains(E[] array) {
        if (array == null || array.length == 0 || size == 0) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (!contains(array[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean contains(Collection<E> collection) {
        if (collection == null || collection.size() == 0 || size == 0) {
            return false;
        }

        Iterator<E> it = collection.iterator();

        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean reverse() {
        if (size <= 1) {
            return true;
        }

        int count = 0;
        inode = head;

        while (count < size) {
            DoublyLinkedNode<E> temp = inode.getPrev();
            inode.setPrev(inode.getNext());
            inode.setNext(temp);
            inode = inode.getPrev();
            count++;
        }

        DoublyLinkedNode<E> temp = head;
        head = tail;
        tail = temp;

        head.setPrev(tail);
        tail.setNext(head);

        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void forEach(Function<E, Void> action) {
        if (action == null || size == 0) {
            return;
        }

        int count = 0;
        inode = head;

        while (count < size) {
            action.apply(inode.get());
            inode = inode.getNext();
            count++;
        }
    }

    @Override
    public Iterator<E> iterator() {
        inode = head;

        return new Iterator<E>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public E next() {
                E element = inode.get();
                inode = inode.getNext();
                count++;
                return element;
            }
        };
    }

}
