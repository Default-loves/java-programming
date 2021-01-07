package com.junyi.guava;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @time: 2021/1/7 14:53
 * @version: 1.0
 * @author: junyi Xu
 * @description: Guava 中一些常规操作的示例
 */
@Slf4j
public class common {

    /** 非空判断 */
    @Test
    public void test() {
        String param = "未读代码";
        String name = Preconditions.checkNotNull(param);
        System.out.println(name); // 未读代码
        String param2 = null;
        String name2 = Preconditions.checkNotNull(param2, "param2 is null"); // NullPointerException
        System.out.println(name2);
    }

    /** 预期值判断 */
    @Test
    public void testValue() {
        String param = "www.wdbyte.com2";
        String wdbyte = "www.wdbyte.com";
        Preconditions.checkArgument(wdbyte.equals(param), "[%s] 404 NOT FOUND", param);
    }

    /** 是否越界 */
    @Test
    public void testOutOfIndex() {
        ArrayList<String> list = Lists.newArrayList("a", "b", "c", "d");
        Preconditions.checkElementIndex(4, list.size());    // 4不是合法的索引
    }

    /** 创建不可变的集合，如果对其进行修改会报错 */
    @Test
    public void immutableSet() {
        // 创建方式1：of
        ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
        immutableSet.forEach(System.out::println);

        // 创建方式2：builder
        ImmutableSet<String> immutableSet2 = ImmutableSet.<String>builder()
                .add("hello")
                .add(new String("未读代码"))
                .build();
        immutableSet2.forEach(System.out::println);

        // 创建方式3：从其他集合中拷贝创建，copyOf()
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("www.wdbyte.com");
        arrayList.add("https");
        ImmutableSet<String> immutableSet3 = ImmutableSet.copyOf(arrayList);
        immutableSet3.forEach(System.out::println);
    }

    /** 创建集合 */
    @Test
    public void buildCollection() {
        // 创建一个 ArrayList 集合
        List<String> list1 = Lists.newArrayList();

        // 创建一个 ArrayList 集合，同时塞入3个数据
        List<String> list2 = Lists.newArrayList("a", "b", "c");

        // 创建一个 ArrayList 集合，容量初始化为10
        List<String> list3 = Lists.newArrayListWithCapacity(10);

        LinkedList<String> linkedList1 = Lists.newLinkedList();
        CopyOnWriteArrayList<String> cowArrayList = Lists.newCopyOnWriteArrayList();

        HashMap<Object, Object> hashMap = Maps.newHashMap();
        ConcurrentMap<Object, Object> concurrentMap = Maps.newConcurrentMap();
        TreeMap<Comparable, Object> treeMap = Maps.newTreeMap();

        HashSet<Object> hashSet = Sets.newHashSet();
        HashSet<String> newHashSet = Sets.newHashSet("a", "a", "b", "c");
    }

    /** 集合操作 */
    @Test
    public void setOperation() {
        Set<String> newHashSet1 = Sets.newHashSet("a", "b", "c");
        Set<String> newHashSet2 = Sets.newHashSet("b", "c", "d");

        // 交集
        Sets.SetView<String> intersectionSet = Sets.intersection(newHashSet1, newHashSet2);
        System.out.println(intersectionSet); // [b, c]

        // 并集
        Sets.SetView<String> unionSet = Sets.union(newHashSet1, newHashSet2);
        System.out.println(unionSet); // [a, b, c, d]

        // newHashSet1 中存在，newHashSet2 中不存在
        Sets.SetView<String> setView = Sets.difference(newHashSet1, newHashSet2);
        System.out.println(setView); // [a]

    }

    /** 对 List 统计元素，汇总元素的个数 */
    @Test
    public void calElement() {
        ArrayList<String> arrayList = Lists.newArrayList("a", "b", "c", "d", "a", "c");
        HashMultiset<String> multiset = HashMultiset.create(arrayList);
        multiset.elementSet().forEach(s -> System.out.println(s + ":" + multiset.count(s)));
        /* result
        a:2
        b:1
        c:2
        d:1 */
    }

    /** 对 Map 统计元素，结果为List */
    @Test
    public void sumElement() {
        HashMultimap<String, String> multimap = HashMultimap.create();
        multimap.put("狗", "大黄");
        multimap.put("狗", "旺财");
        multimap.put("猫", "加菲");
        multimap.put("猫", "汤姆");
        System.out.println(multimap.get("猫")); // [加菲, 汤姆]
    }
}
