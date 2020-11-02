package cn.bestsort.model.enums;

import java.util.stream.Stream;

import org.springframework.util.Assert;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 17:11
 */
public interface KeyValEnum<T> {

    /**
     * 根据Enum获取对应的key
     * @return 对应的key
     */
    T getKey();

    /**
     * 获取默认值
     * @return 默认值
     */
    T getDefault();

    /**
     * 获取其枚举类的默认值
     * @param keyValEnum 对应枚举
     * @param <T>       对应枚举的类型
     * @return          默认值
     */
    static <T> T getDefault(KeyValEnum<T> keyValEnum) {
        return keyValEnum.getDefault();
    }
    /**
     * 将enum中的default值转换为对应类型
     * @param clazz     目标类型
     * @param enumType  从哪个枚举类获取
     * @param <V>       结果值
     * @return          内容为enumType中存储的默认值, 类型为clazz对应的类型
     */
    static <V> V get(Class<V> clazz, KeyValEnum enumType) {
        return clazz.cast(enumType.getDefault());
    }
    /**
     * 将value转换为对应enum
     * @param enumType enum type
     * @param value    database value
     * @param <V>      value generic
     * @param <E>      enum generic
     * @return corresponding enum
     */
    static <V, E extends KeyValEnum<V>> E valueToEnum(Class<E> enumType, V value) {
        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(value, "value must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
            .filter(item -> item.getKey().equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("unknown database value: " + value));
    }
}
