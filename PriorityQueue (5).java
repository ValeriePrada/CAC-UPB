package co.edu.upb.proyecto.datastructures.app.linkedlist.doubly;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;
import co.edu.upb.proyecto.datastructures.model.list.AbstractList;
import co.edu.upb.proyecto.datastructures.model.list.List;
import co.edu.upb.proyecto.datastructures.app.linkedlist.node.doubly.DoublyLinkedNode;


public class DoublyLinkedList<E> extends AbstractList<E>{

    private transient DoublyLinkedNode<E> head;
    private transient DoublyLinkedNode<E> tail;
    private transient DoublyLinkedNode<E> inode;


    public DoublyLinkedList() {
        head = tail = null;
        size = 0;
    }

    public DoublyLinkedList(E element) {
        DoublyLinkedNode<E> node = new DoublyLinkedNode<>(element);
        head = tail = node;
        size = 1;
    }

    public boolean add(E element) {
        try {
            DoublyLinkedNode<E> node = new DoublyLinkedNode<>(element);

            if (isEmpty()) {
                head = tail = node;
            } else {
                tail.setNext(node);
                node.setPrev(tail);
                tail = node;
            }

            size++;
            return true;
        } 
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        String result = "";
        DoublyLinkedNode<E> node = head;

        while (node != null) {
            result += node.get();
            if (node.getNext() != null) {
                result += " <-> ";
            }
            node = node.getNext();
        }

        return result;
    }

    @Override
    public boolean add(E[] array) {
        if (array == null) {
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
        try {
            DoublyLinkedNode<E> node = new DoublyLinkedNode<>(element);

            if (isEmpty()) {
                head = tail = node;
            } else {
                node.setNext(head);
                head.setPrev(node);
                head = node;
            }

            size++;
            return true;
        } 
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addFirst(E[] array) {
        if (array == null) {
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
        if (collection == null || collection.size() == 0){
            return false;
        } 

        boolean added = false;
        
        int n = collection.size();
        E[] elements = (E[]) new Object[n];
        int i = 0;
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            elements[i++] = it.next();
        }

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
        if (head == null || n <= 0) {
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
        if (head == null || n <= 0) {
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
        DoublyLinkedList<E> result = new DoublyLinkedList<>();

        if (head == null || n <= 0) {
            return result;
        }

        DoublyLinkedNode<E> node = head;
        int count = 0;

        while (node != null && count < n) {
            result.add(node.get());
            node = node.getNext();
            count++;
        }

        return result;
    }

    @Override
    public List<E> peekLastCollection(int n) {
        DoublyLinkedList<E> result = new DoublyLinkedList<>();

        if (head == null || n <= 0) {
            return result;
        }

        int start = size - n;
        if (start < 0) {
            start = 0;
        }

        DoublyLinkedNode<E> node = head;
        int count = 0;
        while (node != null) {
            if (count >= start) {
                result.add(node.get());
            }
            node = node.getNext();
            count++;
        }

        return result;
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;

            E element = head.get();
            head = head.getNext();

            if (head != null) {
                head.setPrev(null);
            } else {
                tail = null;
            }

            size--;
            return element;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) return null;

            E element = tail.get();
            tail = tail.getPrev();

            if (tail != null) {
                tail.setNext(null);
            } else {
                head = null;
            }

            size--;
            return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] pollArray(int n) {
        if (head == null || n <= 0) {
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
        if (head == null || n <= 0) {
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
        DoublyLinkedList<E> result = new DoublyLinkedList<>();

        if (head == null || n <= 0) {
            return result;
        }

        int count = 0;
        while (head != null && count < n) {
            result.add(poll());
            count++;
        }

        return result;
    }

    @Override
    public List<E> pollLastCollection(int n) {
        DoublyLinkedList<E> result = new DoublyLinkedList<>();

        if (head == null || n <= 0) {
            return result;
        }

        int start = n;
        DoublyLinkedNode<E> node = tail;
        while (node != null && start > 0) {
            result.addFirst(node.get());
            node = node.getPrev();
            start--;
        }

        for (int i = 0; i < n && !isEmpty(); i++) {
            pollLast();
        }

        return result;
    }

    @Override
    public boolean remove(E element) {
        if (isEmpty()){
            return false;
        }

        DoublyLinkedNode<E> node = head;

        while (node != null) {
            if (node.get().equals(element)) {

                if (node == head) {
                    poll();
                } else if (node == tail) {
                    pollLast();
                } else {
                    node.getPrev().setNext(node.getNext());
                    node.getNext().setPrev(node.getPrev());
                    size--;
                }

                return true;
            }

            node = node.getNext();
        }

        return false;
    }

    @Override
    public boolean remove(E[] array) {
        if (head == null || array == null || array.length == 0) {
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
        if (head == null || collection == null || collection.size() == 0) {
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
        if (head == null || filter == null) {
            return false;
        }

        boolean removedAny = false;

        while (head != null && filter.test(head.get())) {
            poll();
            removedAny = true;
        }

        DoublyLinkedNode<E> node = head;
        while (node != null) {
            if (node.getNext() != null && filter.test(node.getNext().get())) {
                DoublyLinkedNode<E> toRemove = node.getNext();
                if (toRemove == tail) {
                    pollLast();
                } else {
                    node.setNext(toRemove.getNext());
                    toRemove.getNext().setPrev(node);
                    size--;
                }
                removedAny = true;
            } else {
                node = node.getNext();
            }
        }

        return removedAny;
    }

    @Override
    public boolean replace(E element, E newElement, Predicate<E> comparator) {
        if (head == null || comparator == null) {
            return false;
        }

        boolean replacedAny = false;
        DoublyLinkedNode<E> node = head;

        while (node != null) {
            if (comparator.test(node.get()) && node.get().equals(element)) {
                node.set(newElement);
                replacedAny = true;
            }
            node = node.getNext();
        }

        return replacedAny;
    }

    @Override
    public boolean replace(E[] array, E[] newArray, Predicate<E> comparator) {
        if (head == null || array == null || newArray == null || array.length != newArray.length) {
            return false;
        }

        boolean replacedAny = false;

        for (int i = 0; i < array.length; i++) {
            DoublyLinkedNode<E> node = head;
            while (node != null) {
                if (comparator.test(node.get()) && node.get().equals(array[i])) {
                    node.set(newArray[i]);
                    replacedAny = true;
                }
                node = node.getNext();
            }
        }

        return replacedAny;
    }

    @Override
    public boolean replace(Collection<E> collection, Collection<E> newCollection, Predicate<E> comparator) {
        if (head == null || collection == null || newCollection == null || collection.size() != newCollection.size()) {
            return false;
        }

        boolean replacedAny = false;
        Iterator<E> itOld = collection.iterator();
        Iterator<E> itNew = newCollection.iterator();

        while (itOld.hasNext() && itNew.hasNext()) {
            E oldElement = itOld.next();
            E newElement = itNew.next();

            DoublyLinkedNode<E> node = head;
            while (node != null) {
                if (comparator.test(node.get()) && node.get().equals(oldElement)) {
                    node.set(newElement);
                    replacedAny = true;
                }
                node = node.getNext();
            }
        }

        return replacedAny;
    }

    @Override
    public boolean retain(E[] array) {
        if (head == null) {
            return false;
        }

        if (array == null || array.length == 0) {
            clear();
            return true;
        }

        boolean modified = false;
        DoublyLinkedNode<E> node = head;

        while (node != null) {
            boolean found = false;
            for (int i = 0; i < array.length; i++) {
                if (node.get().equals(array[i])) {
                    found = true;
                    break;
                }
            }

            DoublyLinkedNode<E> nextNode = node.getNext();
            if (!found) {
                remove(node.get());
                modified = true;
            }
            node = nextNode;
        }

        return modified;
    }

    @Override
    public boolean retain(Collection<E> collection) {
        if (head == null) {
            return false;
        }

        if (collection == null || collection.size() == 0) {
            clear();
            return true;
        }

        boolean modified = false;
        DoublyLinkedNode<E> node = head;

        while (node != null) {
            DoublyLinkedNode<E> nextNode = node.getNext();
            if (!collection.contains(node.get())) {
                remove(node.get());
                modified = true;
            }
            node = nextNode;
        }

        return modified;
    }

    @Override
    public boolean set(E index, E element) {
        if (head == null || index == null || element == null) {
            return false;
        }

        DoublyLinkedNode<E> node = head;
        int count = 0;
        int idx = (Integer) index;

        while (node != null) {
            if (count == idx) {
                node.set(element);
                return true;
            }
            node = node.getNext();
            count++;
        }

        return false;
    }

    @Override
    public boolean sort(ToIntFunction<E> toInt) {
        if (head == null || size <= 1 || toInt == null){
            return true;
        }

        for (int i = size; i > 1; i--) {
            inode = head;
            for (int j = 1; j < i; j++) {
                DoublyLinkedNode<E> nextNode = inode.getNext();
                if (toInt.applyAsInt(inode.get()) > toInt.applyAsInt(nextNode.get())) {
                    E temp = inode.get();
                    inode.set(nextNode.get());
                    nextNode.set(temp);
                }
                inode = nextNode;
            }
        }

        return true;
    }

    @Override
    public List<E> subList(E from, E to) {
        
        DoublyLinkedList<E> result = new DoublyLinkedList<>();
        if (head == null) {
            return result;
        }

        DoublyLinkedNode<E> node = head;
        boolean adding = false;

        while (node != null) {
            if (node.get().equals(from)) {
                adding = true;
            }

            if (adding) {
                result.add(node.get());
            }

            if (node.get().equals(to)) {
                break;
            }

            node = node.getNext();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        if (head == null) {
            return null;
        }

        E[] array = (E[]) new Object[size];
        DoublyLinkedNode<E> node = head;
        int i = 0;

        while (node != null) {
            array[i] = node.get();
            node = node.getNext();
            i++;
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
        DoublyLinkedNode<E> node = head;

        while (node != null) {
            if (node.get().equals(element)){
                return true;
            }

            node = node.getNext();
        }

        return false;
    }

    @Override
    public boolean contains(E[] array) {
        if (head == null || array == null || array.length == 0) {
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
        if (head == null || collection == null || collection.size() == 0) {
            return false;
        }

        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            E element = it.next();
            if (!contains(element)) {
                return false;
            }
        }

    return true;
  }

    @Override
    public boolean reverse() {
        if (isEmpty() || size == 1){
            return true;
        }

        inode = head;
        int count = 0;

        while (inode != null && count < size) {
            DoublyLinkedNode<E> temp = inode.getPrev();
            inode.setPrev(inode.getNext());
            inode.setNext(temp);

            inode = inode.getPrev();
            count++;
        }

        DoublyLinkedNode<E> temp = head;
        head = tail;
        tail = temp;

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Function<E, Void> action) {
        if (head == null || action == null) {
            return;
        }

        DoublyLinkedNode<E> node = head;
        while (node != null) {
            action.apply(node.get());
            node = node.getNext();
        }
    }

  @Override
  public Iterator<E> iterator() {
    inode = head;

        return new Iterator<E>() {

            public boolean hasNext() {
                return inode != null;
            }

            public E next() {
                E element = inode.get();
                inode = inode.getNext();
                return element;
            }
        };
    }

}
