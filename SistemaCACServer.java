package co.edu.upb.proyecto.datastructures.model.array;

public interface BufferArray {
  
  void defragment();

  /**
   * Resizes the array to the specified dimension. If the specified dimension is
   * less than the current dimension, the array is truncated.
   * 
   * @param newDimension the new dimension of the array.
   * @return 'true' if the array was re dimensioned successfully, otherwise
   *         'false'.
   */
  boolean dimension(int newDimension);
}
