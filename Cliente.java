package co.edu.upb.proyecto.datastructures.app.priorityqueue;

import java.util.function.Function;

import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;
import co.edu.upb.proyecto.datastructures.model.priorityqueue.AbstractPriorityQueue;
import co.edu.upb.proyecto.datastructures.app.array.ArrayImpl;

public class PriorityQueue<E> extends AbstractPriorityQueue<E>{

    private ArrayImpl<E> array;

    public PriorityQueue(int capacity) {
        this.array = new ArrayImpl<>(capacity);
    }

    public E get(int index){
        return array.get(index);
    }

    @Override
    public boolean insert(E element) {
        return array.add(element);
    }

    public boolean add(int index, E element){
        return array.add(index, element); // 🔥 usa tu ArrayImpl
    }

    @Override
    public boolean insert(int index, E element) {

        if(index < 0 || index > array.size()){
            System.out.println("El indice es inválido: " + index);
            return false;
        }

        array.add(index, element); // 🔥 AQUÍ ESTÁ LA CLAVE
        return true;
    }
    

    @Override
    public E peek() {
        if(array.isEmpty()){
            System.out.println("La cola de prioridad está vacía.");
            return null;
        }
        return array.get(0);
    }

    @Override
    public E extract() {
        if(array.isEmpty()){
            System.out.println("La cola de prioridad está vacía.");
            return null;
        }
        E element = array.get(0);
        array.remove(0);
        return element;
    }

    @Override
    public boolean clear() {
        return array.clear();
    }

    @Override
    public boolean contains(E element) {
        for(int ii = 0; ii < array.size(); ii++){
            if(array.get(ii).equals(element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(E[] array) {
        for(E element : array){
            if(!contains(element)){
                return false;
            }
        }
        return true;
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
        for(int ii = 0; ii < array.size(); ii++){
            action.apply(array.get(ii));
        }
    }

    public boolean remove(int index){
        return array.remove(index);
    }

    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }
}
