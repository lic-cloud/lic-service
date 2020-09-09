package cn.bestsort.constant;

import cn.bestsort.exception.LicException;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 16:37
 */
public interface ExceptionConstant {

    // 4XXX为用户错误


    /**
     * 移动、重命名时已存在目标文件
     */
    LicException TARGET_EXIST = new LicException("目标文件/文件夹已存在", 4001);

    LicException VERIFICATION_FAILED = new LicException("字段核验不通过", 4002);

    LicException LIC_INSTALLED  = new LicException("应用已初始化, 请勿重复操作", 4003);

    LicException UNAUTHORIZED   = new LicException("无权限, 请尝试登录或者联系管理员分配权限", 4004);
    LicException USER_HAS_BEEN_LOCKED = new LicException("用户已被冻结", 4005);

    /**
     * 5XXX为系统错误
     */
    LicException NOT_FOUND_FILE_SYS = new LicException("未找到对应的文件系统实现", 5001);
    LicException NOT_FOUND_ITEM = new LicException("目标未找到", 4004);
}
