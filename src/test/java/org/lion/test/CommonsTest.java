package org.lion.test;

import org.junit.Assert;
import org.junit.Test;
import org.lion.beans.Person;
import org.lion.utils.Commons;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lion on 17-9-15.
 */
public class CommonsTest {
    @Test
    public void testSplitAndroidId() throws Exception {
        Assert.assertEquals("timepicker", Commons.splitAndroidId("@+id/timepicker"));
        Assert.assertEquals("", Commons.splitAndroidId("@+id/"));
        Assert.assertEquals("@+id", Commons.splitAndroidId("@+id"));
        Assert.assertEquals("", Commons.splitAndroidId(""));
    }

    @Test
    public void testSimpleClassName() throws Exception {
        Assert.assertEquals("a", Commons.simpleClassName("a"));
        Assert.assertEquals("a", Commons.simpleClassName("b.a"));
        Assert.assertEquals("", Commons.simpleClassName(""));
        Assert.assertEquals("", Commons.simpleClassName("a."));
    }

    @Test
    public void testFileNameWithoutExt() throws Exception {
        Assert.assertEquals("a", Commons.fileNameWithoutExt("a.java"));
        Assert.assertEquals("a", Commons.fileNameWithoutExt("b/a.java"));
        Assert.assertEquals("a", Commons.fileNameWithoutExt("b/a"));
        Assert.assertEquals("", Commons.fileNameWithoutExt(""));

    }

    @Test
    public void testBinarySearch() throws Exception {

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (Integer.valueOf(2).equals(next)) {
                list.add(2);
            }
        }
    }

    @Test
    public void test57() throws Exception {
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
    }


    @Test
    public void test67() throws Exception {
        Person lion = new Person("lion", 20);
        Person more = new Person("more", 39);
        ArrayList<Person> people = new ArrayList<>();
        people.add(lion);
        people.add(more);

        List<Person> people1 = Collections.unmodifiableList(people);
        people1.get(0).setAge(0);


    }


    @Test
    public void test85() throws Exception {
        Stream.of("333", "4444", "1", "22")
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Test
    public void test90() throws Exception {
        try (Stream<String> lines = Files.lines(Paths.get("/usr/share/dict/american-english"));) {
            Map<Integer, List<String>> map = lines.filter(s -> s.length() > 20)
                    .collect(Collectors.groupingBy(
                            String::length,
                            Collectors.toList()
                    ));
            System.out.println(map);

        } catch (Exception e) {

        }
    }

    @Test
    public void test107() throws Exception {
        Collector<String, TreeSet<String>, SortedSet<String>> setCollector = Collector.of(TreeSet<String>::new, SortedSet<String>::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                Collections::unmodifiableSortedSet);
        SortedSet<String> collect = Stream.of("lion", "cat", "dog", "bird")
                .collect(setCollector);
        collect.add("abc");
    }

    @Test
    public void test119() throws Exception {
        new Random().ints(10, 0, 10)
                .sorted()
                .forEach(System.out::println);
    }

    private Map<Long, BigInteger> cache = new HashMap<>();

    @Test
    public void test128() throws Exception {
        System.out.println(fib(1000));
    }

    private BigInteger fib(long i) {
        if (i == 0) return BigInteger.ZERO;
        if (i == 1) return BigInteger.ONE;

        return cache.computeIfAbsent(i, n -> fib(n - 1).add(fib(n - 2)));
    }

    @Test
    public void test142() throws Exception {
        Optional<String> first = List.of("111")
                .stream()
                .filter(s -> s.length() % 2 == 0)
                .findFirst();
        System.out.println(first.orElse("nothing"));
    }

    @Test
    public void test151() throws Exception {
        try (Stream<Path> files = Files.list(Paths.get("/tmp"))) {
            files.forEach(System.out::println);
        } catch (Exception e) {
        }
    }

    @Test
    public void test160() throws Exception {
        try (Stream<Path> pathStream = Files.walk(Paths.get("/home/lion/tmp"))) {
            pathStream.forEach(System.out::println);
        } catch (Exception e) {
        }
    }

    @Test
    public void test168() throws Exception {

    }
}