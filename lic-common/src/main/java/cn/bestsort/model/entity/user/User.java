package cn.bestsort.model.entity.user;

import java.util.Date;

import javax.persistence.Entity;

import cn.bestsort.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends BaseEntity {
    private String username;
    private String password;
    private String nickname;
    private String headImgUrl;
    private String phone;
    private String telephone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Integer sex;
    private Integer status;
    private String intro;

    public interface Status {
        int DISABLED = 0;
        int VALID = 1;
        int LOCKED = 2;
    }
}
