package cn.bestsort.model.enums;

/**
 * 引导页步骤
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 15:27
 */
public enum  InstallStepEnum {
    /**
     * 开始初始化设置
     */
    START,
    /**
     * 注册账户
     */
    REGISTER,
    /**
     * 设置缓存
     */
    SETTING_CACHE,
    /**
     * 设置文件系统实现
     */
    SETTING_FILE_SYS,
    /**
     * 完成
     */
    FINISHED;

    public InstallStepEnum next(InstallStepEnum stepEnum) {
        InstallStepEnum res = null;

        for (InstallStepEnum value : InstallStepEnum.values()) {
            if (stepEnum.equals(res)) {
                res = value;
                break;
            }
            if (stepEnum.equals(value)) {
                res = stepEnum;
            }
        }
        return res;
    }
}
