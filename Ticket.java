package co.edu.upb.proyecto.datastructures.app.stack.array;

import co.edu.upb.proyecto.datastructures.model.stack.AbstractStack;

import java.util.function.Function;

import co.edu.upb.proyecto.datastructures.app.array.ArrayImpl;
import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;

public class Stack<E> extends AbstractStack<E>  {

    private ArrayImpl<E> array;
    private int top;


    public Stack(int capacity) {
        this.array = new ArrayImpl<>(capacity);
        this.top = -1;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return array.get(top);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        E element = array.get(top);
        array.set(top, null); 
        top--;
        return element;
    }

    @Override
    public boolean push(E element) {
        if (top >= array.size() - 1) {
            System.out.println("La pila está llena");
            return false;
        }
        top++;
        return array.set(top, element);
    }

    @Override
    public boolean clear() {
        return array.clear();
    }

    @Override
    public boolean contains(E element) {
        return array.contains(element);
    }

    @Override
    public boolean contains(E[] array) {
        return this.array.contains(array);
    }

    @Override
    public boolean contains(Collection<E> collection) {
        return array.contains(collection);
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public boolean reverse() {
        return array.reverse();
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public void forEach(Function<E, Void> action) {
        array.forEach(action);
    }

    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }
}
