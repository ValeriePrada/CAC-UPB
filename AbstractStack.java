package co.edu.upb.proyecto.datastructures.app.linkedlist.node.doubly;

public class DoublyLinkedNode<E> {
    
    private E element;
    private DoublyLinkedNode<E> next;
    private DoublyLinkedNode<E> prev;

    public DoublyLinkedNode(E element) {
        this.element = element;
    }

    public E get() {
        return element;
    }

    public void set(E element) {
        this.element = element;
    }

    public DoublyLinkedNode<E> getNext() {
        return next;
    }

    public void setNext(DoublyLinkedNode<E> next) {
        this.next = next;
    }

    public DoublyLinkedNode<E> getPrev() {
        return prev;
    }

    public void setPrev(DoublyLinkedNode<E> prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
