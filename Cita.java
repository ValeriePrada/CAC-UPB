package co.edu.upb.proyecto.datastructures.app.linkedlist.singly;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;
import co.edu.upb.proyecto.datastructures.model.list.AbstractList;
import co.edu.upb.proyecto.datastructures.model.list.List;
import co.edu.upb.proyecto.datastructures.app.linkedlist.node.singly.LinkedNode;

public class LinkedList<E> extends AbstractList<E> {

  private transient LinkedNode<E> head;
  private transient LinkedNode<E> tail;
  private transient LinkedNode<E> inode;

    public LinkedList() {
        head = tail = null;
        size = 0;
    }

    public LinkedList(E element) {
        LinkedNode<E> node = new LinkedNode<>(element);
        head = tail = node;
        size = 1;
    }

    public boolean add(E element) {
        try {
        if (isEmpty()) {
            LinkedNode<E> node = new LinkedNode<>(element);
            this.head = this.tail = this.inode = node;
        } else {
            LinkedNode<E> node = new LinkedNode<>(element);
            this.tail.setNext(node);
            this.tail = node;
        }
        this.size++;
        return true;
        } catch (Exception e) {
        System.out.println(e.getMessage());
        return false;
        }

    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public String toString() {
        return "LinkedList [head=" + head + "]";
    }

    @Override
    public boolean add(E[] array) {
        if (array == null || array.length == 0){
            return false;
        }
        for (E element : array) {
            add(element); 
        }
        return true;
    }

    @Override
    public boolean add(Collection<E> collection) {
        if (collection == null || collection.size() == 0) return false;

        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            add(it.next()); 
        }

        return true;
    }

  @Override
  public boolean addFirst(E element) {
        try {
            LinkedNode<E> node = new LinkedNode<>(element);

            if (isEmpty()) {
                head = tail = node;
            } else {
                node.setNext(head);
                head = node;
            }

            size++;
            return true;

        } catch (Exception e) {
            return false;
        }

    }

  @Override
  public boolean addFirst(E[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (int i = array.length - 1; i >= 0; i--) {
            addFirst(array[i]);
        }
        return true;
    }

  @Override
  public boolean addFirst(Collection<E> collection) {
        if (collection == null || collection.isEmpty()){
            return false;
        } 

        LinkedList<E> temp = new LinkedList<>();

        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            temp.addFirst(it.next());
        }

        Iterator<E> reverse = temp.iterator();
        while (reverse.hasNext()) {
            addFirst(reverse.next());
        }

        return true;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return this.head.get();
    }

    @Override
    public E peekLast() {
        return this.tail.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] peekArray(int n) {
        if (isEmpty() || n <= 0){
            return null;
        }

        int limit = n;
        if (n > size) {
            limit = size;
        }

        E[] result = (E[]) new Object[limit];

        LinkedNode<E> node = head;

        for (int i = 0; i < limit; i++) {
            result[i] = node.get();
            node = node.getNext();
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] peekLastArray(int n) {
        if (isEmpty() || n <= 0){
            return null;
        }

        int listSize = size();
        int limit = n;
        if (n > listSize){
            limit = listSize;
        }

        E[] array = (E[]) new Object[limit];

        int startIndex = listSize - limit;
        LinkedNode<E> node = head;
        int count = 0;
        int i = 0;

        while (node != null) {
            if (count >= startIndex) {
                array[i] = node.get();
                i++;
            }
            node = node.getNext();
            count++;
        }

        return array;
    }

    @Override
    public List<E> peekCollection(int n) {
        if (isEmpty() || n <= 0) return new LinkedList<>();

        LinkedList<E> result = new LinkedList<>();
        LinkedNode<E> node = head;
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
        if (isEmpty() || n <= 0){
            return new LinkedList<>();
        }

        LinkedList<E> result = new LinkedList<>();

        int startIndex = size - n;
        if (startIndex < 0) {
            startIndex = 0;
        }

        LinkedNode<E> node = head;
        int count = 0;

        while (node != null) {
            if (count >= startIndex) {
                result.add(node.get());
            }
            node = node.getNext();
            count++;
        }

        return result;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        E element = head.get();
        head = head.getNext();

        if (head == null) {
            tail = null;
        }

        size--;
        return element;
        
        }

    @Override
    public E pollLast() {
        if (isEmpty()){
            return null;
        }

        if (head == tail) {
            E element = head.get();
            clear();
            return element;
        }
        LinkedNode<E> node = head;
        while (node.getNext() != tail) {
            node = node.getNext();
        }

        E element = tail.get();
        tail = node;
        tail.setNext(null);
        size--;

        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] pollArray(int n) {
        if (isEmpty() || n <= 0){
            return null;
        }

        int limit = n;
        if (n > size) limit = size; 

        E[] array = (E[]) new Object[limit];

        for (int i = 0; i < limit; i++) {
            array[i] = poll(); 
        }

        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] pollLastArray(int n) {
        if (isEmpty() || n <= 0) {
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
        if (isEmpty() || n <= 0) {
            return new LinkedList<>();
        }

        LinkedList<E> result = new LinkedList<>();
        int count = 0;

        while (!isEmpty() && count < n) {
            result.add(poll()); 
            count++;
        }

        return result;
    }

    @Override
    public List<E> pollLastCollection(int n) {
        if (isEmpty() || n <= 0) {
            return new LinkedList<>();
        }

        LinkedList<E> result = new LinkedList<>();
        int count = 0;

        while (!isEmpty() && count < n) {
            result.addFirst(pollLast());
            count++;
        }

        return result;
    }

    @Override
    public boolean remove(E element) {
        if (isEmpty()) {
            return false;
        }

        if (head.get().equals(element)) {
            head = head.getNext();
            size--;
            if (size == 0) tail = null;
            return true;
        }

        LinkedNode<E> node = head;
        while (node.getNext() != null) {
            if (node.getNext().get().equals(element)) {
                node.setNext(node.getNext().getNext());
                if (node.getNext() == null) {
                    tail = node;
                }
                size--;
                return true;
            }
            node = node.getNext();
        }

        return false;
    }

    @Override
    public boolean remove(E[] array) {
        if (isEmpty() || array == null || array.length == 0){
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
        if (isEmpty() || collection == null || collection.size() == 0) {
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
        if (isEmpty() || filter == null) {
            return false;
        }

        boolean removedAny = false;

        while (head != null && filter.test(head.get())) {
            head = head.getNext();
            removedAny = true;
            size--;
        }

        if (head == null) {
            tail = null;
            return removedAny;
        }

        LinkedNode<E> node = head;
        while (node.getNext() != null) {
            if (filter.test(node.getNext().get())) {
                node.setNext(node.getNext().getNext());
                if (node.getNext() == null) {
                    tail = node;
                }
                removedAny = true;
                size--;
            } else {
                node = node.getNext();
            }
        }

        return removedAny;
    }

    @Override
    public boolean replace(E element, E newElement, Predicate<E> comparator) {
        if (isEmpty()) {
            return false;
        }

        boolean replacedAny = false;
        LinkedNode<E> node = head;

        while (node != null) {
            if (comparator.test(node.get())) { 
                node.set(newElement);
                replacedAny = true;
            }
            node = node.getNext();
        }

        return replacedAny;
    }

    @Override
    public boolean replace(E[] array, E[] newArray, Predicate<E> comparator) {
        if (isEmpty() || array == null || newArray == null || array.length != newArray.length) {
            return false;
        }

        boolean replacedAny = false;

        for (int i = 0; i < array.length; i++) {
            LinkedNode<E> node = head;
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
        if (isEmpty() || collection == null || newCollection == null || collection.size() != newCollection.size()) {
            return false;
        }

        boolean replacedAny = false;

        Iterator<E> itOld = collection.iterator();
        Iterator<E> itNew = newCollection.iterator();

        while (itOld.hasNext() && itNew.hasNext()) {
            E oldElement = itOld.next();
            E newElement = itNew.next();

            LinkedNode<E> node = head;
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
        if (isEmpty()) {
            return false;
        }
        if (array == null || array.length == 0) {
            clear();
            return true;
        }

        boolean modified = false;
        LinkedNode<E> node = head;
        LinkedNode<E> prev = null;

        while (node != null) {
            boolean found = false;
            for (int i = 0; i < array.length; i++) {
                if (node.get().equals(array[i])) {
                    found = true;
                    break;
                }
            }

            if (!found) { 
                if (node == head) {
                    head = head.getNext();
                    if (head == null) {
                        tail = null;
                    }
                    node = head;
                } else {
                    prev.setNext(node.getNext());
                    if (node.getNext() == null){
                        tail = prev;
                    }
                    node = prev.getNext();
                }
                size--;
                modified = true;
            } else {
                prev = node;
                node = node.getNext();
            }
        }

        return modified;
    }

    @Override
    public boolean retain(Collection<E> collection) {
        if (isEmpty()) {
            return false;
        }
        if (collection == null || collection.size() == 0) {
            clear();
            return true;
        }

        boolean modified = false;
        LinkedNode<E> node = head;
        LinkedNode<E> prev = null;

        while (node != null) {
            if (!collection.contains(node.get())) { 
                if (node == head) {
                    head = head.getNext();
                    if (head == null) tail = null;
                    node = head;
                } else {
                    prev.setNext(node.getNext());
                    if (node.getNext() == null) {
                        tail = prev;
                    }
                    node = prev.getNext();
                }
                size--;
                modified = true;
            } else {
                prev = node;
                node = node.getNext();
            }
        }

        return modified;
    }

    @Override
    public boolean set(E index, E element) {
        if (isEmpty() || index == null || element == null) {
            return false;
        }

        LinkedNode<E> node = head;
        int count = 0;

        while (node != null) {
            if (count == (Integer) index) { 
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
        if (isEmpty() || size == 1) return true;

        boolean change;

        do {
            change = false;
            LinkedNode<E> node = head;

            while (node.getNext() != null) {

                if (toInt.applyAsInt(node.get()) >
                    toInt.applyAsInt(node.getNext().get())) {

                    E value = node.get();
                    node.set(node.getNext().get());
                    node.getNext().set(value);

                    change = true;
                }

                node = node.getNext();
            }

        } while (change);

        return true;
    }

    @Override
    public List<E> subList(E from, E to) {
        LinkedList<E> result = new LinkedList<>();
        inode = head;

        while (inode != null && !inode.get().equals(from)) {
            inode = inode.getNext();
        }

        while (inode != null) {
            result.add(inode.get());
            if (inode.get().equals(to)) break;
            inode = inode.getNext();
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        if (isEmpty()) {
            return null;
        }

        E[] array = (E[]) new Object[size];
        LinkedNode<E> node = head;
        int i = 0;
        while (node != null) {
            array[i++] = node.get();
            node = node.getNext();
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
        LinkedNode<E> node = head;
        while (node != null) {
            if (node.get().equals(element)) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    @Override
    public boolean contains(E[] array) {
        if (isEmpty() || array == null || array.length == 0) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            boolean found = false;
            LinkedNode<E> node = head;

            while (node != null) {
                if (node.get().equals(array[i])) {
                    found = true;
                    break; 
                }
                node = node.getNext();
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean contains(Collection<E> collection) {
        if (isEmpty() || collection == null || collection.size() == 0) {
            return false;
        }

        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            E element = it.next();
            boolean found = false;

            LinkedNode<E> node = head;
            while (node != null) {
                if (node.get().equals(element)) {
                    found = true;
                    break;
                }
                node = node.getNext();
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean reverse() {
        if (isEmpty() || size == 1) {
            return true;
        }

        LinkedNode<E> prev = null;
        LinkedNode<E> node = head;
        tail = head; 

        while (node != null) {
            LinkedNode<E> nextNode = node.getNext();
            node.setNext(prev);
            prev = node;
            node = nextNode;
        }

        head = prev;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Function<E, Void> action) {
        if (isEmpty() || action == null) {
                return;
        }

        LinkedNode<E> node = head;
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
            if (!hasNext()) {
            throw new IllegalStateException("No more elements in the iterator");
            }

            E element = inode.get();
            inode = inode.getNext();
            return element;
        }
        };
    }

}
