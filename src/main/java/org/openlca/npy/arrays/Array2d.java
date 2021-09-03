package org.openlca.npy.arrays;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.openlca.npy.Npy;
import org.openlca.npy.NpyHeader;

public final class Array2d {

  private Array2d() {
  }

  /**
   * Checks if the given array is a valid 2-dimensional array. We do not check
   * this in the other utility methods of this class. So if you are not sure
   * if an array is a 2D array you should call this method to check this before
   * calling the other methods of this class.
   *
   * @param array the NPY array to check
   * @return {@code true} if the array has 2 dimensions and each dimensions is
   * {@code > 1}.
   */
  public static boolean isValid(NpyArray<?> array) {
    if (array == null || array.shape() == null)
      return false;
    var shape = array.shape();
    int rowCount = shape[0];
    int colCount = shape[1];
    return rowCount > 0
      && colCount > 0
      && rowCount * colCount == array.size();
  }

  public static int rowCountOf(NpyArray<?> array) {
    return array.shape()[0];
  }

  public static int columnCountOf(NpyArray<?> array) {
    return array.shape()[1];
  }

  public static int indexOf(NpyArray<?> array, int row, int col) {
    if (array.hasColumnOrder()) {
      int rows = array.shape()[0];
      return col * rows + row;
    } else {
      int cols = array.shape()[1];
      return row * cols + col;
    }
  }

  public static boolean get(NpyBooleanArray array, int row, int col) {
    int i = indexOf(array, row, col);
    return array.data[i];
  }

  public static boolean[] getRow(NpyBooleanArray array, int row) {
    int cols = array.shape[1];
    if (!array.hasColumnOrder()) {
      int offset = row * cols;
      return Arrays.copyOfRange(array.data, offset, offset + cols);
    }
    int rows = array.shape[0];
    boolean[] values = new boolean[cols];
    int offset = 0;
    for (int col = 0; col < cols; col++) {
      values[col] = array.data[offset + row];
      offset += rows;
    }
    return values;
  }

  public static boolean[] getColumn(NpyBooleanArray array, int col) {
    int rows = array.shape[0];
    if (array.hasColumnOrder()) {
      int offset = col * rows;
      return Arrays.copyOfRange(array.data, offset, offset + rows);
    }
    int cols = array.shape[1];
    boolean[] values = new boolean[rows];
    int offset = 0;
    for (int row = 0; row < rows; row++) {
      values[row] = array.data[offset + col];
      offset += cols;
    }
    return values;
  }

  public static byte get(NpyByteArray array, int row, int col) {
    int i = indexOf(array, row, col);
    return array.data[i];
  }

  public static byte[] getRow(NpyByteArray array, int row) {
    int cols = array.shape[1];
    if (!array.hasColumnOrder()) {
      int offset = row * cols;
      return Arrays.copyOfRange(array.data, offset, offset + cols);
    }
    int rows = array.shape[0];
    byte[] values = new byte[cols];
    int offset = 0;
    for (int col = 0; col < cols; col++) {
      values[col] = array.data[offset + row];
      offset += rows;
    }
    return values;
  }

  public static byte[] getColumn(NpyByteArray array, int col) {
    int rows = array.shape[0];
    if (array.hasColumnOrder()) {
      int offset = col * rows;
      return Arrays.copyOfRange(array.data, offset, offset + rows);
    }
    int cols = array.shape[1];
    byte[] values = new byte[rows];
    int offset = 0;
    for (int row = 0; row < rows; row++) {
      values[row] = array.data[offset + col];
      offset += cols;
    }
    return values;
  }

  public static double get(NpyDoubleArray array, int row, int col) {
    int i = indexOf(array, row, col);
    return array.data[i];
  }

  public static double[] getRow(NpyDoubleArray array, int row) {
    int cols = array.shape[1];
    if (!array.hasColumnOrder()) {
      int offset = row * cols;
      return Arrays.copyOfRange(array.data, offset, offset + cols);
    }
    int rows = array.shape[0];
    double[] values = new double[cols];
    int offset = 0;
    for (int col = 0; col < cols; col++) {
      values[col] = array.data[offset + row];
      offset += rows;
    }
    return values;
  }

  public static double[] getColumn(NpyDoubleArray array, int col) {
    int rows = array.shape[0];
    if (array.hasColumnOrder()) {
      int offset = col * rows;
      return Arrays.copyOfRange(array.data, offset, offset + rows);
    }
    int cols = array.shape[1];
    double[] values = new double[rows];
    int offset = 0;
    for (int row = 0; row < rows; row++) {
      values[row] = array.data[offset + col];
      offset += cols;
    }
    return values;
  }

  public static float get(NpyFloatArray array, int row, int col) {
    int i = indexOf(array, row, col);
    return array.data[i];
  }

  public static float[] getRow(NpyFloatArray array, int row) {
    int cols = array.shape[1];
    if (!array.hasColumnOrder()) {
      int offset = row * cols;
      return Arrays.copyOfRange(array.data, offset, offset + cols);
    }
    int rows = array.shape[0];
    float[] values = new float[cols];
    int offset = 0;
    for (int col = 0; col < cols; col++) {
      values[col] = array.data[offset + row];
      offset += rows;
    }
    return values;
  }

  public static float[] getColumn(NpyFloatArray array, int col) {
    int rows = array.shape[0];
    if (array.hasColumnOrder()) {
      int offset = col * rows;
      return Arrays.copyOfRange(array.data, offset, offset + rows);
    }
    int cols = array.shape[1];
    float[] values = new float[rows];
    int offset = 0;
    for (int row = 0; row < rows; row++) {
      values[row] = array.data[offset + col];
      offset += cols;
    }
    return values;
  }

  public static int get(NpyIntArray array, int row, int col) {
    int i = indexOf(array, row, col);
    return array.data[i];
  }

  public static int[] getRow(NpyIntArray array, int row) {
    int cols = array.shape[1];
    if (!array.hasColumnOrder()) {
      int offset = row * cols;
      return Arrays.copyOfRange(array.data, offset, offset + cols);
    }
    int rows = array.shape[0];
    int[] values = new int[cols];
    int offset = 0;
    for (int col = 0; col < cols; col++) {
      values[col] = array.data[offset + row];
      offset += rows;
    }
    return values;
  }

  public static int[] getColumn(NpyIntArray array, int col) {
    int rows = array.shape[0];
    if (array.hasColumnOrder()) {
      int offset = col * rows;
      return Arrays.copyOfRange(array.data, offset, offset + rows);
    }
    int cols = array.shape[1];
    int[] values = new int[rows];
    int offset = 0;
    for (int row = 0; row < rows; row++) {
      values[row] = array.data[offset + col];
      offset += cols;
    }
    return values;
  }

  public static long get(NpyLongArray array, int row, int col) {
    int i = indexOf(array, row, col);
    return array.data[i];
  }

  public static long[] getRow(NpyLongArray array, int row) {
    int cols = array.shape[1];
    if (!array.hasColumnOrder()) {
      int offset = row * cols;
      return Arrays.copyOfRange(array.data, offset, offset + cols);
    }
    int rows = array.shape[0];
    long[] values = new long[cols];
    int offset = 0;
    for (int col = 0; col < cols; col++) {
      values[col] = array.data[offset + row];
      offset += rows;
    }
    return values;
  }

  public static long[] getColumn(NpyLongArray array, int col) {
    int rows = array.shape[0];
    if (array.hasColumnOrder()) {
      int offset = col * rows;
      return Arrays.copyOfRange(array.data, offset, offset + rows);
    }
    int cols = array.shape[1];
    long[] values = new long[rows];
    int offset = 0;
    for (int row = 0; row < rows; row++) {
      values[row] = array.data[offset + col];
      offset += cols;
    }
    return values;
  }

  public static short get(NpyShortArray array, int row, int col) {
    int i = indexOf(array, row, col);
    return array.data[i];
  }

  public static short[] getRow(NpyShortArray array, int row) {
    int cols = array.shape[1];
    if (!array.hasColumnOrder()) {
      int offset = row * cols;
      return Arrays.copyOfRange(array.data, offset, offset + cols);
    }
    int rows = array.shape[0];
    short[] values = new short[cols];
    int offset = 0;
    for (int col = 0; col < cols; col++) {
      values[col] = array.data[offset + row];
      offset += rows;
    }
    return values;
  }

  public static short[] getColumn(NpyShortArray array, int col) {
    int rows = array.shape[0];
    if (array.hasColumnOrder()) {
      int offset = col * rows;
      return Arrays.copyOfRange(array.data, offset, offset + rows);
    }
    int cols = array.shape[1];
    short[] values = new short[rows];
    int offset = 0;
    for (int row = 0; row < rows; row++) {
      values[row] = array.data[offset + col];
      offset += cols;
    }
    return values;
  }

  public static NpyArray<?> readRow(File file, int row) {
    try (var raf = new RandomAccessFile(file, "r");
         var channel = raf.getChannel()) {
      var header = NpyHeader.read(channel);
      return readRow(raf, header, row);
    } catch (IOException e) {
      throw new RuntimeException(
        "failed to read a row " + row + " from NPY file " + file, e);
    }
  }

  public static NpyArray<?> readRow(
    RandomAccessFile file, NpyHeader header, int row) {
    var dict = header.dict();
    int rows = dict.sizeOfDimension(0);
    int columns = dict.sizeOfDimension(1);
    return dict.hasFortranOrder()
      ? Npy.readElements(file, header, columns, row, rows)
      : Npy.readRange(file, header, columns, row * columns);
  }

  public static NpyArray<?> readColumn(File file, int column) {
    try (var raf = new RandomAccessFile(file, "r");
         var channel = raf.getChannel()) {
      var header = NpyHeader.read(channel);
      return readColumn(raf, header, column);
    } catch (IOException e) {
      throw new RuntimeException(
        "failed to read a column " + column + " from NPY file " + file, e);
    }
  }

  public static NpyArray<?> readColumn(
    RandomAccessFile file, NpyHeader header, int column) {
    var dict = header.dict();
    int rows = dict.sizeOfDimension(0);
    int columns = dict.sizeOfDimension(1);
    return dict.hasFortranOrder()
      ? Npy.readRange(file, header, rows, column * rows)
      : Npy.readElements(file, header, rows, column, columns);
  }

  public static <T extends NpyArray<?>> T switchOrder(T array) {
    return OrderSwitch2d.of(array);
  }
}