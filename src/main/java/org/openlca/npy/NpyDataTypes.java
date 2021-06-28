package org.openlca.npy;

import java.nio.ByteOrder;

/**
 * An enumeration of some fixed size NumPy data types.
 *
 * @see <a href="https://numpy.org/doc/stable/reference/arrays.dtypes.html">
 * https://numpy.org/doc/stable/reference/arrays.dtypes.html</a>
 */
public enum NpyDataTypes implements NpyDataType {

  /**
   * Booleans
   */
  bool("?", 1, new String[]{
    "b1",
    "bool",
    "bool8",
    "bool_",}),

  /**
   * 16 bit floating point numbers
   */
  f2("f2", 2, new String[]{
    "e",
    "float16",
    "half",
  }),

  /**
   * 32 bit floating point numbers
   */
  f4("f4", 4, new String[]{
    "f",
    "float32",
    "single",
  }),

  /**
   * 64 bit floating point numbers
   */
  f8("f8", 8, new String[]{
    "d",
    "double",
    "float",
    "float64",
    "float_",
    "g",
    "longdouble",
    "longfloat",
  }),

  /**
   * 8-bit signed integers
   */
  i1("b", 1, new String[]{
    "byte",
    "i1",
    "int8"}),

  /**
   * 16 bit signed integers
   */
  i2("i2", 2, new String[]{
    "h",
    "int16",
    "short",
  }),

  /**
   * 32 bit signed integers
   */
  i4("i4", 4, new String[]{
    "i",
    "int",
    "int32",
    "int_",
    "intc",
    "l",
    "long",
  }),

  /**
   * 64 bit signed integers
   */
  i8("i8", 8, new String[]{
    "int0",
    "int64",
    "intp",
    "longlong",
    "p",
    "q",
  }),

  /**
   * 8-bit unsigned integers
   */
  u1("B", 1, new String[]{
    "u1",
    "ubyte",
    "uint8",
  }),

  /**
   * 16 bit unsigned integers
   */
  u2("u2", 2, new String[]{
    "H",
    "uint16",
    "ushort",
  }),

  /**
   * 32 bit unsigned integers
   */
  u4("u4", 4, new String[]{
    "I",
    "L",
    "uint",
    "uint32",
    "uintc",
  }),

  /**
   * 64 bit unsigned integers
   */
  u8("u8", 8, new String[]{
    "P",
    "Q",
    "Uint64",
    "uint0",
    "uint64",
    "uintp",
    "ulonglong",
  });

  private final String symbol;

  private final int size;
  private final String[] synonyms;

  NpyDataTypes(String symbol, int size, String[] synonyms) {
    this.symbol = symbol;
    this.size = size;
    this.synonyms = synonyms;
  }

  @Override
  public String toString() {
    return symbol;
  }

  @Override
  public String symbol() {
    return symbol;
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * Tries to find the data type for the given symbol. The behavior is a bit
   * like {@code numpy.dtype('[the type name or symbol]')}. The given symbol
   * can have a prefix that indicates the byte order.
   *
   * @param dtype the data type symbol (e.g. {@code i4, int32, <i4})
   * @return the data type or {@code null} if there is no matching type defined.
   */
  public static NpyDataType of(String dtype) {
    if (dtype == null || dtype.length() == 0)
      return null;
    char first = dtype.charAt(0);
    boolean hasOrderMark = first == '<'
      || first == '>'
      || first == '='
      || first == '|';
    var symbol = hasOrderMark
      ? dtype.substring(1)
      : dtype;

    for (var type : NpyDataTypes.values()) {
      if (symbol.equals(type.symbol()))
        return type;
      for (var name : type.synonyms) {
        if (symbol.equals(name))
          return type;
      }
    }

    // string types
    var ascii = NpyAsciiType.parse(dtype);
    if (ascii.isPresent())
      return ascii.get();
    var unicode = NpyUnicodeType.parse(dtype);
    return unicode.orElse(null);

  }

  /**
   * Get the byte order of the given data type string. It tries to identify it
   * from the first character of that type string:
   * <ul>
   *   <li>{@code =} hardware native</li>
   *   <li>{@code <} little-endian</li>
   *   <li>{@code >} big-endian</li>
   *   <li>{@code |} not applicable</li>
   * </ul>
   *
   * @param dtype the data type string (e.g. {@code <i4})
   * @return the identified byte-order or the platform native order if it is
   * not specified in the given type string
   * @see <a href="https://numpy.org/doc/stable/reference/generated/numpy.dtype.byteorder.html">
   * https://numpy.org/doc/stable/reference/generated/numpy.dtype.byteorder.html</a>
   */
  public static ByteOrder byteOrderOf(String dtype) {
    if (dtype == null || dtype.length() == 0)
      return ByteOrder.nativeOrder();
    switch (dtype.charAt(0)) {
      case '>':
        return ByteOrder.BIG_ENDIAN;
      case '<':
        return ByteOrder.LITTLE_ENDIAN;
      default:
        return ByteOrder.nativeOrder();
    }
  }
}
