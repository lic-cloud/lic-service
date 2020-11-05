package cn.bestsort.model.enums;

import cn.bestsort.constant.ExceptionConstant;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-11-05 06:24
 */
public enum InitStep implements KeyEnum<String> {
    STEP_1("register_root_user"),
    STEP_2("file_sys_config"),
    STEP_3("cache_sys_config"),
    FINISH("finished");
    private final String name;
    InitStep(String stepName) {
        this.name = stepName;
    }
    @Override
    public String getKey() {
        return this.name;
    }

    public static int indexOf(String name) {
        InitStep[] values = InitStep.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getKey().equals(name)) {
                return i;
            }
        }
        throw ExceptionConstant.NOT_FOUND_ITEM;
    }
}
