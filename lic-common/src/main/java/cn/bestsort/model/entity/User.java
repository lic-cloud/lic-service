package cn.bestsort.model.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "sys_user", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "phone"),
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "telephone")
})
public class User extends BaseEntity {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$", message = "字母开头，5-16位，允许字母数字下划线")
    private String username;
    @NotBlank(message = "昵称不能为空")
    @Length(min = 3, max = 10, message = "昵称的长度为3-10位")
    private String nickname;
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "手机号格式有误")
    private String phone;
    @NotBlank(message = "电子邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;
    @NotNull(message = "生日不能为空")
    private Timestamp birthday;
    @NotNull(message = "性别不能为空")
    private Integer sex;
    private String password;
    private String telephone;
    private Integer status;
    private String headImgUrl;
    private float totalCapacity;
    private float usedCapacity;

    /**
     * 是否无限容量
     */
    private Boolean infiniteCapacity;

    public interface Status {
        int DISABLED = 0;
        int VALID = 1;
        int LOCKED = 2;
    }
}
