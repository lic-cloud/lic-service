package cn.bestsort.model.enums.file;

import java.io.File;

import cn.bestsort.model.enums.ValueEnum;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 17:29
 */
public enum  LocalHostMetaEnum implements ValueEnum<String> {
    /**
     * 文件存储地址
     */
    DATA_DIR("data" + File.separator),
    ROOT_PATH(System.getProperty("user.home") + File.separator);

    private final String value;

    private final String defaultVal;

    LocalHostMetaEnum(String defaultVal) {
        this.value = "local_file_" + this.name().toLowerCase();
        this.defaultVal = defaultVal;
    }

    @Override
    public String getVal() {
        return this.value;
    }

    @Override
    public String getDefault() {
        return this.defaultVal;
    }
}
