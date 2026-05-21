package co.edu.upb.proyecto.datastructures.app.linkedlist.circular;

import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.Predicate;

import co.edu.upb.proyecto.datastructures.app.linkedlist.node.singly.LinkedNode;
import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;
import co.edu.upb.proyecto.datastructures.model.list.AbstractList;
import co.edu.upb.proyecto.datastructures.model.list.List;

public class LinkedList<E> extends AbstractList<E> {

    private transient LinkedNode<E> head;
    private transient LinkedNode<E> tail;
    private transient LinkedNode<E> inode;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public LinkedList(E element) {
        LinkedNode<E> node = new LinkedNode<>(element);
        head = node;
        tail = node;
        tail.setNext(head);
        size = 1;
    }

    public boolean add(E element) {
        if (element == null) {
            return false;
        }
        LinkedNode<E> node = new LinkedNode<>(element);
        if (size == 0) {
            head = node;
            tail = node;
            tail.setNext(head);
        } else {
            tail.setNext(node);
            tail = node;
            tail.setNext(head);
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
        if (size == 0) {
            return "";
        }
        String text = "";
        inode = head;
        int count = 0;
        while (count < size) {
            text += inode.get();
            if (count < size - 1) {
                text += " -> ";
            }
            inode = inode.getNext();
            count++;
        }
        return text;
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
        LinkedNode<E> node = new LinkedNode<>(element);
        if (size == 0) {
            head = node;
            tail = node;
            tail.setNext(head);
        } else {
            node.setNext(head);
            head = node;
            tail.setNext(head);
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
        if (collection == null || collection.isEmpty()) return false;

        boolean added = false;
        int n = collection.size();
        E[] elements = (E[]) new Object[n];
        int index = 0;

        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            elements[index++] = it.next();
        }

        for (int i = n - 1; i >= 0; i--) {
            if (addFirst(elements[i])) {
                added = true;
            }
    }
    return added;
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return head.get();
    }

    @Override
    public E peekLast() {
        if (size == 0) {
            return null;
        }
        return tail.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] peekArray(int n) {
        if (size == 0 || n <= 0) {
            return null;
        }
        if (n > size) {
            n = size;
        }
        E[] array = (E[]) new Object[n];
        inode = head;
        int count = 0;
        while (count < n) {
            array[count] = inode.get();
            inode = inode.getNext();
            count++;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] peekLastArray(int n) {
        if (size == 0 || n <= 0) {
            return null;
        }
        if (n > size) {
            n = size;
        }
        E[] array = (E[]) new Object[n];
        inode = head;
        int skip = size - n;
        int count = 0;
        while (count < skip) {
            inode = inode.getNext();
            count++;
        }
        count = 0;
        while (count < n) {
            array[count] = inode.get();
            inode = inode.getNext();
            count++;
        }
        return array;
    }

    @Override
    public List<E> peekCollection(int n) {
        LinkedList<E> list = new LinkedList<>();
        if (size == 0 || n <= 0){
            return list;
        } 
        if (n > size) {
            n = size;
        }

        inode = head;
        int count = 0;
        while (count < n) {
            list.add(inode.get());
            inode = inode.getNext();
            count++;
        }
        return list;
    }

    @Override
    public List<E> peekLastCollection(int n) {
        LinkedList<E> list = new LinkedList<>();
        if (size == 0 || n <= 0) {
            return list;
        }
        if (n > size){
            n = size;
        } 

        inode = head;
        int skip = size - n;
        int count = 0;
        while (count < skip) { 
            inode = inode.getNext();
            count++;
        }
        count = 0;
        while (count < n) {
            list.add(inode.get());
            inode = inode.getNext();
            count++;
        }
        return list;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E element = head.get();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
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
            head = null;
            tail = null;
        } else {
            inode = head;

            while (inode.getNext() != tail) {
                inode = inode.getNext();
            }

            tail = inode;
            tail.setNext(head);
        }

        size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] pollArray(int n) {
        if (size == 0 || n <= 0) {
            return null;
        }
        if (n > size) {
            n = size;
        }
        E[] array = (E[]) new Object[n];
        int count = 0;
        while (count < n) {
            array[count] = poll();
            count++;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] pollLastArray(int n) {
        if (size == 0 || n <= 0) {
            return null;
        }
        if (n > size) {
            n = size;
        }
        E[] array = (E[]) new Object[n];
        for (int i = n - 1; i >= 0; i--) {
            array[i] = pollLast();
        }
        return array;
    }

    @Override
    public List<E> pollCollection(int n) {
        LinkedList<E> list = new LinkedList<>();
        if (size == 0 || n <= 0) {
            return list;
        }
        if (n > size) {
            n = size;
        }
        for (int i = 0; i < n; i++) {
            list.add(poll());
        }
        return list;
    }

    @Override
    public List<E> pollLastCollection(int n) {
        LinkedList<E> list = new LinkedList<>();
        if (size == 0 || n <= 0) {
            return list;
        }
        if (n > size) {
            n = size;
        }
        for (int i = 0; i < n; i++) {
            list.addFirst(pollLast());
        }
        return list;
    }

    @Override
    public boolean remove(E element) {
        if (size == 0 || element == null) {
            return false;
        }
        inode = head;
        LinkedNode<E> previous = tail;
        int count = 0;
        while (count < size) {
            if (inode.get().equals(element)) {
                if (size == 1) {
                    head = null;
                    tail = null;
                } else {
                    previous.setNext(inode.getNext());
                    if (inode == head) {
                        head = inode.getNext();
                    }
                    if (inode == tail) {
                        tail = previous;
                    }
                    tail.setNext(head);
                }
                size--;
                return true;
            }
            previous = inode;
            inode = inode.getNext();
            count++;
        }
        return false;
    }

    @Override
    public boolean remove(E[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        boolean removed = false;
        for (int i = 0; i < array.length; i++) {
            if (remove(array[i])) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean remove(Collection<E> collection) {
        if (collection == null || collection.size() == 0) {
            return false;
        }
        boolean removed = false;
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            if (remove(it.next())) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean remove(Predicate<E> filter) {
        if (size == 0 || filter == null){
            return false;
        }
        boolean removed = false;
        int originalSize = size;
        inode = head;
        LinkedNode<E> previous = tail;
        int count = 0;

        while (count < originalSize && size > 0) {

            if (filter.test(inode.get())) {

                if (size == 1) {
                    head = null;
                    tail = null;
                } else {
                    previous.setNext(inode.getNext());

                    if (inode == head) {
                        head = inode.getNext();
                    }
                    if (inode == tail){
                        tail = previous;
                    }
                    tail.setNext(head);
                }

                inode = previous.getNext();
                size--;
                removed = true;

            } else {
                previous = inode;
                inode = inode.getNext();
            }

            count++;
        }

        return removed;
    }

    @Override
    public boolean replace(E element, E newElement, Predicate<E> comparator) {
        if (size == 0 || comparator == null){
            return false;
        }

        inode = head;
        int count = 0;

        while (count < size) {

            if (comparator.test(inode.get()) && inode.get().equals(element)) {
                inode.set(newElement);
                return true;
            }

            inode = inode.getNext();
            count++;
        }

        return false;
    }

    @Override
    public boolean replace(E[] array, E[] newArray, Predicate<E> comparator) {
        if (array == null || newArray == null || array.length != newArray.length || comparator == null){
            return false;
        }
        boolean replaced = false;
        for (int i = 0; i < array.length; i++) {
            if (replace(array[i], newArray[i], comparator)) {
                replaced = true;
            }
        }
        return replaced;
    }

    @Override
    public boolean replace(Collection<E> collection, Collection<E> newCollection, Predicate<E> comparator) {
        if (collection == null || newCollection == null || collection.size() != newCollection.size() || comparator == null){
            return false;
        } 

        boolean replaced = false;
        Iterator<E> itOld = collection.iterator();
        Iterator<E> itNew = newCollection.iterator();

        while (itOld.hasNext() && itNew.hasNext()) {
            if (replace(itOld.next(), itNew.next(), comparator)){
                replaced = true;
            }
        }
        return replaced;
    }

    @Override
    public boolean retain(E[] array) {
        if (isEmpty()) {
            return false;
        }

        int originalSize = size;
        int count = 0;
        boolean modified = false;

        while (count < originalSize && size > 0) {

            E value = head.get();
            boolean keep = false;

            for (int i = 0; i < array.length; i++) {
                if (value.equals(array[i])) {
                    keep = true;
                    break;
                }
            }

            if (!keep) {
                remove(value);
                modified = true;
            } else {
                head = head.getNext();
                tail = tail.getNext();
            }

            count++;
        }

        return modified;
    }

    @Override
    public boolean retain(Collection<E> collection) {
        if (isEmpty()) {
        return false;
    }

    int originalSize = size;
    int count = 0;
    boolean modified = false;

    while (count < originalSize && size > 0) {

        E value = head.get();

        if (!collection.contains(value)) {
            remove(value);
            modified = true;
        } else {
            head = head.getNext();
            tail = tail.getNext();
        }

        count++;
    }

    return modified;
    }

    @Override
    public boolean set(E index, E element) {
        if (element == null){
            return false;
        }

        if (!(index instanceof Integer)) {
            return false;
        }
        int pos = (Integer) index;

        if (pos < 0 || pos >= size) return false;

        inode = head;
        int count = 0;
        while (count < pos) {
            inode = inode.getNext();
            count++;
        }

        inode.set(element);
        return true;
    }

    @Override
    public boolean sort(ToIntFunction<E> toInt) {
        if (size <= 1 || toInt == null) {
            return true;
        }

        for (int i = 0; i < size; i++) {
            inode = head;
            for (int j = 0; j < size - 1; j++) {
                LinkedNode<E> nextNode = inode.getNext();

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
        LinkedList<E> list = new LinkedList<>();
        if (size == 0 || from == null || to == null) return list;

        boolean started = false;
        inode = head;
        int count = 0;
        while (count < size) {
            if (inode.get().equals(from)) {
                started = true;
            }
            if (started) {
                list.add(inode.get());
                if (inode.get().equals(to)){
                    break;
                }
            }
            inode = inode.getNext();
            count++;
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        if (size == 0) {
            return null;
        }
        E[] array = (E[]) new Object[size];
        inode = head;
        int index = 0;
        while (index < size) {
            array[index] = inode.get();
            inode = inode.getNext();
            index++;
        }
        return array;
    }

    @Override
    public boolean clear() {
        head = null;
        tail = null;
        inode = null;
        size = 0;
        return true;
    }

    @Override
    public boolean contains(E element) {
        if (size == 0 || element == null) {
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
        if (array == null || array.length == 0) {
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
        if (collection == null || collection.size() == 0) {
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
        if (size <= 1){
            return true;
        }

        LinkedNode<E> previous = tail;
        inode = head;
        LinkedNode<E> next;

        int count = 0;

        while (count < size) {
            next = inode.getNext();
            inode.setNext(previous);
            previous = inode;
            inode = next;
            count++;
        }

        LinkedNode<E> temp = head;
        head = tail;
        tail = temp;

        tail.setNext(head);

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Function<E, Void> action) {
        if (size == 0 || action == null) {
            return;
        }
        inode = head;
        int count = 0;
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
            private int count = 0;

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
