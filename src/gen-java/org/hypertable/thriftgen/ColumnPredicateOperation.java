/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.hypertable.thriftgen;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * The "operation" for a ColumnPredicate
 * 
 * EXACT_MATCH: compares the cell value for identity
 *     (... WHERE column = "value")
 * PREFIX_MATCH: compares the cell value for a prefix match
 *     (... WHERE column =^ "prefix")
 */
public enum ColumnPredicateOperation implements org.apache.thrift.TEnum {
  EXACT_MATCH(1),
  PREFIX_MATCH(2);

  private final int value;

  private ColumnPredicateOperation(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ColumnPredicateOperation findByValue(int value) { 
    switch (value) {
      case 1:
        return EXACT_MATCH;
      case 2:
        return PREFIX_MATCH;
      default:
        return null;
    }
  }
}
