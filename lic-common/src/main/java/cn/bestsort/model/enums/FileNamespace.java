package cn.bestsort.model.enums;

/**
 * 文件系统相关类别
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 10:57
 */
public enum  FileNamespace implements KeyEnum<String> {
    /**
     * 服务器本地
     */
    LOCALHOST("server"),
    /**
     * 阿里OSS
     */
    ALI_OSS("ali_oss"),
    /**
     * 腾讯COS
     */
    TEN_COS("tencent_oss");

    public static FileNamespace parse(String val) {
        return FileNamespace.valueOf(val);
    }
    private final String name;
    FileNamespace(String name) {
        this.name = name;
    }
    @Override
    public String getKey() {
        return name;
    }
}
