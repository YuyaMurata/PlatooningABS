/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

/**
 *
 * @author murata
 */
import java.util.List;
import java.util.ArrayList;

/**
 * 小さい値から取り出すヒープ (最後に取り出した値より小さい値を push してはいけない).
 */
public final class RadixHeap {

  private final List<List<Double>> v = new ArrayList<>();
  private Double last = Double.NEGATIVE_INFINITY;
  private int    size;

  public RadixHeap() {
    for (int i = 0; i <= 64; i++) {
      v.add(new ArrayList<Double>());
    }
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  public int size() {
    return this.size;
  }

  public void push(Double x) {
    if (last.compareTo(x) > 0) {
      throw new IllegalArgumentException();
    }

    size++;
    this.put(x, last);
  }

  public Double pop() {
    if (size <= 0) {
      throw new java.util.EmptyStackException();
    }

    List<Double> head = v.get(0);
    if (head.isEmpty()) {
      List<Double> list = getValidList(v);
      this.last = getMinValue(list);
      this.replaceWithLast(list);
    }
    size--;

    head.remove(head.size() - 1);
    return this.last;
  }

  /**
   * 空でない最小のリストを返す.
   */
  private static List<Double> getValidList(List<List<Double>> lists) {
    for (List<Double> list : lists) {
      if (!list.isEmpty()) {
        return list;
      }
    }
    return null;
  }

  /**
   * this.last に最小値を設定する.
   */
  private static Double getMinValue(List<Double> a) {
    Double y = a.get(0);
    for (Double x : a) {
      if (y.compareTo(x) > 0) {
        y = x;
      }
    }
    return y;
  }

  /**
   * 最小値 (this.last) に基づいて再配置する.
   */
  private void replaceWithLast(List<Double> a) {
    Double l = this.last;
    for (Double x : a) {
      put(x, l);
    }
    a.clear();
  }

  private void put(Double x, Double l) {
    v.get(bsr(encode(x)^encode(l))+1).add(x);
  }

  /**
   * 浮動小数点数の大小関係を保存して符号なし整数に変換する.
   */
  private static long encode(Double x) {
    long bits = Double.doubleToRawLongBits(x.doubleValue());
    return bits ^ ((bits >= 0) ? 0x8000000000000000L : 0xFFFFFFFFFFFFFFFFL);
  }

  /**
   * 最も左のビットの位置を返す.
   */
  private static int bsr(long bits) {
    return 63 - Long.numberOfLeadingZeros(bits);
  }

  public static void main(String[] args) {
    RadixHeap heap = new RadixHeap();
    for (int i = 0; i < 20; i++) {
      heap.push(10000.0 * (2.0 * Math.random() - 1.0));
    }
    heap.push(Double.valueOf(-0.0));
    heap.push(Double.valueOf(+0.0));
    heap.push(Double.NEGATIVE_INFINITY);
    heap.push(Double.POSITIVE_INFINITY);
    heap.push(Double.NaN);

    while (!heap.isEmpty()) {
      System.out.println(heap.pop());
    }
  }
}