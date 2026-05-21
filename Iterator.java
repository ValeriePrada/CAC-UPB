package co.edu.upb.proyecto.datastructures.app.array;

import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;
import java.util.function.Predicate;
import java.util.function.Function;
import co.edu.upb.proyecto.datastructures.model.collection.Collection;
import co.edu.upb.proyecto.datastructures.model.array.AbstractArray;

public class ArrayImpl<E> extends AbstractArray<E> {

    private E[] elements;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayImpl(int capacity){
        this.capacity = capacity;
        this.elements = (E[]) new Object[capacity];
        this.size = 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean clear(){
        for(int ii = 0; ii < size; ii++){
            elements[ii] = null;
        }
        size = 0;
        return true;
    }

    @Override
    public E get(int index){
        if(index < 0 || index >= capacity){
            System.out.println("El indice es inválido: " + index);
            return null;
        }
        return elements[index];
    }

    @Override
    public boolean set(int index, E element){
        if(index < 0 || index >= capacity){
            System.out.println("El indice es inválido: " + index);
            return false;
        }
        elements[index] = element;
        if(index >= size) {
            size = index + 1; 
        }
        return true; 
    }


    @Override
    public boolean add(E element){
        if(size >= capacity){
            System.out.println("No se pueden agregar más elementos");
            return false;
        }
        elements[size] = element;
        size++;
        return true;
    }

    public boolean add(int index, E element){

        if(index < 0 || index > size){
            System.out.println("El indice es inválido: " + index);
            return false;
        }

        if(size >= capacity){
            System.out.println("No se pueden agregar más elementos");
            return false;
        }

        for(int i = size - 1; i >= index; i--){
            elements[i + 1] = elements[i];
        }

        elements[index] = element;
        size++;

        return true;
    }

    @Override
    public boolean remove(int index){
        if(index < 0 || index >= size){
            System.out.println("El indice es inválido: " + index);
            return false;
        }
        for(int ii = index; ii < size - 1; ii++){
            elements[ii] = elements[ii + 1];
        }
        elements[size - 1] = null;
        size--;
        return true;
    }

    public E removeAt(int index){

        if(index < 0 || index >= size){
            return null;
        }

        E eliminado = elements[index];

        for(int i = index; i < size - 1; i++){
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        size--;

        return eliminado;
    }

    @Override
    public int indexOf(E element){
        for(int ii = 0; ii < size; ii++){
            if(elements[ii].equals(element)){
                return ii;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E element){
        for(int ii = size - 1; ii >= 0; ii--){
            if(elements[ii].equals(element)){
                return ii;
            }
        }
        return -1;
    }

    @Override
    public boolean remove(Predicate <E> filter){
        for(int ii = 0; ii < size; ii++){
            if(filter.test(elements[ii])){
                for(int jj = ii; jj < size - 1; jj++){
                    elements[jj] = elements[jj + 1];
                }
                elements[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(int from, int to){
        if(from < 0 || to > size || from >= to){
            System.out.println("El rango es inválido en from: " + from + "to: " + to);
            return false;
        }
        int numRemoved = to - from;
        for(int ii =  from; ii < size - numRemoved; ii++){
            elements[ii] = elements[ii + numRemoved];
        }
        for(int ii = size - numRemoved; ii < size; ii++){
            elements[ii] = null;
        }
        size = size - numRemoved;
        return true;
    }

    @Override
    public boolean contains(Collection<E> collection){
        co.edu.upb.proyecto.datastructures.model.iterator.Iterator<E> it = collection.iterator();

    while (it.hasNext()) {
        E element = it.next();
        boolean found = false;

        for (int ii = 0; ii < size; ii++) {
            if (elements[ii] != null && elements[ii].equals(element)) {
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }
    }

    return true;
    }

    @Override
    public boolean reverse(){
        for(int ii = 0; ii < size / 2; ii++){
            E aux = elements[ii];
            elements[ii] = elements[size - 1 - ii];
            elements[size -  1 - ii] = aux;
        }
        return true;
    }

    @Override
    public boolean add(int index, E[] array){
        if(index < 0 || index > size){
            System.out.println("El indice es inválido: " + index);
            return false;
        }
        if(size + array.length > capacity){
            System.out.println("No se pueden agregar más elementos");
            return false;
        }
        for(int ii = size - 1; ii >= index; ii++){
            elements[ii + array.length] = elements[ii];
        }
        for(int ii = 0; ii < array.length; ii++){
            elements[index + ii] = array[ii];
        }
        size = size + array.length;
        return true;
    }

    @Override
    public boolean add(int index, Collection<E> collection){
        if (index < 0 || index > size) {
        System.out.println("El índice es inválido: " + index);
        return false;
    }

    if (collection == null || collection.size() == 0) {
        return false; 
    }

    if (size + collection.size() > capacity) {
        System.out.println("No se pueden agregar más elementos");
        return false;
    }

    for (int i = size - 1; i >= index; i--) {
        elements[i + collection.size()] = elements[i];
    }

    co.edu.upb.proyecto.datastructures.model.iterator.Iterator<E> it = collection.iterator();
    for (int i = index; it.hasNext(); i++) {
        elements[i] = it.next();
    }

    size += collection.size();
    return true;
    }

    @Override
    public void defragment(){
        int newIndex = 0;
        for(int ii = 0; ii < size; ii++){
            if(elements[ii] != null){
                elements[newIndex++] = elements[ii];
            }
        }
        for(int ii = newIndex; ii < capacity; ii++){
            elements[ii] = null;
        }
        size = newIndex;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean dimension(int newDimension){
        if(newDimension <= 0){
            System.out.println("Debe ser mayor a 0");
            return false;
        }
        E[] newArray = (E[]) new Object[newDimension];
        int elementsToCopy;
        if(size < newDimension){
            elementsToCopy = size;
        }else{
            elementsToCopy = newDimension;
        }
        for(int ii = 0; ii < elementsToCopy; ii++){
            newArray[ii] = elements[ii];
        }
        elements = newArray;
        capacity = newDimension;
        size = elementsToCopy;

        return true;
    }

    @Override
    public void forEach(Function<E, Void> action){
        for(int ii = 0; ii < size; ii++){
            action.apply(elements[ii]);
        }
    }

    @Override
    public Iterator<E> iterator(){
        return new Iterator<E>() {
            private int indiceActual = 0;

            @Override
            public boolean hasNext(){
                return indiceActual < size;
            }
            
            @Override
            public E next(){
                if(!hasNext()){
                    System.out.println("No hay más elementos");
                    return null;
                }
                return elements[indiceActual++];
            }
        };
    }
}
