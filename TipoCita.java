package co.edu.upb.proyecto.datastructures.app.stack.list;

import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;
import co.edu.upb.proyecto.datastructures.model.stack.AbstractStack;

import java.util.function.Function;

import co.edu.upb.proyecto.datastructures.app.linkedlist.singly.LinkedList;

public class Stack<E> extends AbstractStack<E> {

    private LinkedList<E> list;

     public Stack() {
        list = new LinkedList<>();
    }

    @Override
    public E peek() {
        return list.peek();
    }

    @Override
    public E pop() {
        return list.poll();
    }

    @Override
    public boolean push(E element) {
        return list.addFirst(element);
    }

    @Override
    public boolean clear() {
        return list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public boolean contains(E[] array) {
        return list.contains(array);
    }

    @Override
    public boolean contains(Collection<E> collection) {
        return list.contains(collection);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean reverse() {
        return list.reverse();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void forEach(Function<E, Void> action) {
        list.forEach(action);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
