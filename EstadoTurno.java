package co.edu.upb.proyecto.datastructures.app.queue.array;

import co.edu.upb.proyecto.datastructures.model.queue.AbstractQueue;

import java.util.function.Function;
import co.edu.upb.proyecto.datastructures.app.array.ArrayImpl;
import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;


public class StaticQueue<E> extends AbstractQueue<E> {

    private ArrayImpl<E> array;

    private int frente;
    private int fin;

    public StaticQueue(int capacity) {
        this.array = new ArrayImpl<>(capacity);
    }
    

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return array.get(frente);
    }

    @Override
    public E extract() {
        if (isEmpty()) {
            return null;
        }
        E element = array.get(frente);
        array.remove(frente);
        fin--;
        return element;
    }

    @Override
    public boolean insert(E element) {
        if(fin == array.size() - 1) {
            return false;
        }
        array.add(element);
        fin++;
        return true;
    }

    @Override
    public boolean clear() {
        return array.clear();
    }

    @Override
    public boolean contains(E element) {
        return array.indexOf(element) != -1;
    }

    @Override
    public boolean contains(E[] elements) {
        for (E element : elements) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(Collection<E> collection) {
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return array.size() == 0;
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
