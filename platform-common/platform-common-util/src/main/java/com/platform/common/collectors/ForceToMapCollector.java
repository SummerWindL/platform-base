package com.platform.common.collectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 将list強制转化为map
 * java8的Collectors.toMap方法存在以下两个问题，该类解决了以下两个问题
 * 1、当key重复时，会抛出异常：java.lang.IllegalStateException: Duplicate key **
 * 2、当value为null时，会抛出异常：java.lang.NullPointerException
 * @author Advance
 * @date 2022年09月27日 17:57
 * @since V1.0.0
 */
public class ForceToMapCollector<T, K, V> implements Collector<T, Map<K, V>, Map<K, V>> {

    /**
     * map的key表达式
     */
    private Function<? super T, ? extends K> keyMapper;

    /**
     * map的value表达式
     */
    private Function<? super T, ? extends V> valueMapper;

    public ForceToMapCollector(Function<? super T, ? extends K> keyMapper,
                               Function<? super T, ? extends V> valueMapper) {
        super();
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }

    @Override
    public BiConsumer<Map<K, V>, T> accumulator() {
        return (map, element) -> map.put(keyMapper.apply(element), valueMapper.apply(element));
    }

    @Override
    public Supplier<Map<K, V>> supplier() {
        return HashMap::new;
    }

    @Override
    public BinaryOperator<Map<K, V>> combiner() {
        return null;
    }

    @Override
    public Function<Map<K, V>, Map<K, V>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    }
}

