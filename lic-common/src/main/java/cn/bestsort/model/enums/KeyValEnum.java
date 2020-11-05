package cn.bestsort.model.enums;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 17:11
 */
public interface KeyValEnum<T> extends KeyEnum<T> {


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
}
