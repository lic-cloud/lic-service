package cn.bestsort.model.enums;

import java.util.stream.Stream;

import lombok.NonNull;
import org.springframework.util.Assert;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-11-05 06:24
 */
public interface KeyEnum<T> {
    /**
     * 根据Enum获取对应的key
     * @return 对应的key
     */
    @NonNull T getKey();

    /**
     * 将value转换为对应enum
     * @param enumType enum type
     * @param key    database key
     * @param <V>      key generic
     * @param <E>      enum generic
     * @return corresponding enum
     */
    static <V, E extends KeyEnum<V>> E keyToEnum(Class<E> enumType, V key) {
        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(key, "key must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
            .filter(item -> item.getKey().equals(key))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("unknown database key: " + key));
    }
}
