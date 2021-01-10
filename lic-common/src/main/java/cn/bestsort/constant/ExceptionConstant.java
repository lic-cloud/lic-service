package cn.bestsort.constant;

import cn.bestsort.exception.LicException;
import cn.bestsort.model.enums.LicMetaEnum;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 16:37
 */
public interface ExceptionConstant {

    /**
     * 移动、重命名时已存在目标文件
     */
    LicException TARGET_EXIST = new LicException("目标文件/文件夹已存在", 4001);
    LicException VERIFICATION_FAILED = new LicException("字段核验不通过", 4002);
    LicException LIC_INSTALLED  = new LicException("应用已初始化, 请勿重复操作", 4003);
    LicException UNAUTHORIZED   = new LicException("无权限, 请尝试登录或者联系管理员分配权限", 4004);
    LicException USER_HAS_BEEN_LOCKED = new LicException("用户已被冻结", 4005);
    LicException PASSWORD_ERROR = new LicException("密码错误", 4006);
    LicException NEED_LOGIN = new LicException("未登录", 4007);
    LicException EXPIRED    = new LicException("已过期", 4008);
    LicException MUST_BE_NOT_DIR = new LicException("目标必须是非文件夹", 4009);
    LicException MUST_BE_DIR = new LicException("目标必须是文件夹", 4010);
    LicException PARAM_ILLEGAL = new LicException("参数不合法，请检查", 4011);

    LicException NOT_FOUND_FILE_SYS = new LicException("未找到对应的文件系统实现", 5001);
    LicException NOT_FOUND_ITEM = new LicException("目标未找到", 4004);
    LicException NOT_FOUND_SUCH_FILE = new LicException("该文件实体不存在", 4005);


    LicException CAN_NOT_MODIFY = new LicException("发布状态的不能修改", 4012);
    LicException TYPE_AND_KEY_EXIT = new LicException("类型和key已存在", 4013);
    LicException USER_NOT_EXIT = new LicException("用户不存在", 4014);
    LicException OLD_PASSWORD_NOT_EXIT = new LicException("旧密码错误", 4015);
    LicException DICTIONARY_IN_USE = new LicException("使用中，无法删除", 4016);
    LicException USER_EXIT = new LicException("用户已存在", 4017);
    LicException ROLE_EXIT = new LicException("角色已存在", 4018);
    LicException PERMISSION_EXIT = new LicException("权限已存在", 4019);
    /**
     * 5XXX为系统错误
     */
    LicException DELETE_FAILED = new LicException("文件删除失败", 5001);

}
