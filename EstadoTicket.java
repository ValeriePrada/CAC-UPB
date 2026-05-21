package co.edu.upb.proyecto.datastructures.app.queue.array;

import co.edu.upb.proyecto.datastructures.model.queue.AbstractQueue;

import java.util.function.Function;
import co.edu.upb.proyecto.datastructures.app.array.ArrayImpl;
import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;


public class AroundQueue<E> extends AbstractQueue<E> {

    private ArrayImpl<E> array;

    private int frente;    
    private int fin;       
    private int size;     
    private int capacity;   

    public AroundQueue(int capacity) {
        this.capacity = capacity;
        this.array = new ArrayImpl<>(capacity);
        this.frente = 0;
        this.fin = 0;
        this.size = 0;
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
        array.set(frente, null); 
        frente = (frente + 1) % capacity;
        size--;
        return element;
    }

    @Override
    public boolean insert(E element) {
        if (size == capacity) {
            return false; 
        }
        array.set(fin, element);
        fin = (fin + 1) % capacity;
        size++;
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
        return size == 0; 
    }

    @Override
    public boolean reverse() {
        return array.reverse();
    }

    @Override
    public int size() {
        return size; 
    }

    @Override
    public void forEach(Function<E, Void> action) {
        int index = frente;
        for (int count = 0; count < size; count++) {
            action.apply(array.get(index));
            index = (index + 1) % capacity;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int count = 0;
            private int index = frente;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    System.out.println("No hay más elementos");
                    return null;
                }
                E elem = array.get(index);
                index = (index + 1) % capacity;
                count++;
                return elem;
            }
        };
    }
}
