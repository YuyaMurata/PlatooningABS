/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author murata
 */
public class GroupTest {
    public static void main(String[] args) {
        Stream<String> s = Stream.of("a", "bar", "c", "foo", "zzz");
        Map<Integer, List<String>> m = s.collect(Collectors.groupingBy(t -> t.length()));
        System.out.println(m);
    }
}
