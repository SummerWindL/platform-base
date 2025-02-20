package com.platform.common.collectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Advance
 * @date 2022年09月27日 17:57
 * @since V1.0.0
 */
public class PlatformCollectors {
    /**
     * 将list強制转化为map
     *
     * @param f1 key表达式
     * @param f2 value表达式
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @return 结果map
     * @see
     * @since 1.0
     */
    public static <T, K, V> Collector<T, ?, Map<K, V>> toMap(Function<T, K> f1,
                                                             Function<T, V> f2) {
        return new ForceToMapCollector<T, K, V>(f1, f2);
    }

    /** Like Collectors.groupingBy, but accepts null keys. */
    public static <T, A> Collector<T, ?, Map<A, List<T>>> groupingBy(Function<? super T, ? extends A> classifier) {
        return Collectors.toMap(classifier, Collections::singletonList,
                (List<T> oldList, List<T> newEl) -> {
                    List<T> newList = new ArrayList<>(oldList.size() + 1);
                    newList.addAll(oldList);
                    newList.addAll(newEl);
                    return newList;
                });
    }
}
