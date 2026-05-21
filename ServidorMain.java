package co.edu.upb.proyecto.datastructures.model.array;

import co.edu.upb.proyecto.datastructures.model.collection.AbstractCollection;
import co.edu.upb.proyecto.datastructures.model.iterator.Iterator;

public abstract class AbstractArray<E> extends AbstractCollection<E> implements Array<E>, BufferArray {

  @Override
  public boolean contains(E element) {
    for (Iterator<E> iterator = iterator(); iterator.hasNext();) {
      if (iterator.next().equals(element)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean contains(E[] array) {
    for (E element : array) {
      if (!contains(element)) {
        return false;
      }
    }
    return true;
  }

}
