package net.sf.anathema.lib.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table<K1, K2, V> {

  private final List<K1> rowKeys = new ArrayList<K1>();
  private final List<K2> columnKeys = new ArrayList<K2>();
  private final List<List<V>> rowMap = new ArrayList<List<V>>();
  private final List<List<V>> columnMap = new ArrayList<List<V>>();

  public void add(K1 rowKey, K2 columnKey, V value) {
    if (!rowKeys.contains(rowKey)) {
      rowKeys.add(rowKey);
      rowMap.add(new ArrayList<V>());

    }
    if (!columnKeys.contains(columnKey)) {
      columnKeys.add(columnKey);
      columnMap.add(new ArrayList<V>());
    }
    int rowIndex = rowKeys.indexOf(rowKey);
    int columnIndex = columnKeys.indexOf(columnKey);
    addToList(rowMap.get(rowIndex), value, columnIndex);
    addToList(columnMap.get(columnIndex), value, rowIndex);
  }

  private void addToList(List<V> list, V value, int index) {
    if (index != -1 && index < list.size()) {
      list.remove(index);
      list.add(index, value);
      return;
    }
    for (int fillIndex = list.size(); fillIndex < index; fillIndex++) {
      list.add(null);
    }
    list.add(value);
  }

  public V get(K1 rowKey, K2 columnKey) {
    int rowIndex = rowKeys.indexOf(rowKey);
    int columnIndex = columnKeys.indexOf(columnKey);
    if (rowIndex == -1 || columnIndex == -1) {
      return null;
    }
    V rowValue = getFromListList(rowMap, rowIndex, columnIndex);
    V columnValue = getFromListList(columnMap, columnIndex, rowIndex);
    if (rowValue == null || columnValue == null || !rowValue.equals(columnValue)) {
      return null;
    }
    return rowValue;
  }

  private V getFromListList(List<List<V>> list, int primaryIndex, int secondaryIndex) {
    List<V> innerList = list.get(primaryIndex);
    if (secondaryIndex >= innerList.size()) {
      return null;
    }
    return innerList.get(secondaryIndex);
  }

  public Set<K1> getPrimaryKeys() {
    return new HashSet<K1>(rowKeys);
  }

  public boolean contains(K1 rowKey, K2 columnKey) {
    return get(rowKey, columnKey) != null;
  }

  public int getSize() {
    return rowKeys.size() * columnKeys.size();
  }
}