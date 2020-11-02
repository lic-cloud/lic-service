package cn.bestsort.model.vo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.annotation.JSONField;

import cn.bestsort.model.dto.PermissionDTO;
import cn.bestsort.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUserVO extends User implements UserDetails {

    private List<PermissionDTO> permissions;
    private String              token;
    /**
     * 登陆时间戳（毫秒）    
     */
    private Long loginTime;
    /**
     * 过期时间戳
     */
    private Long expireTime;

    /**
     * 获取非空的用户权限集合,在Controller每个请求接口上会有注解，校验用户是否有此请求权限
     * 如@PreAuthorize("hasAuthority('sys:user:add')")
     * 此注解会调用本方法，校验用户是否有此请求权限
     */
    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions == null ? null : permissions.parallelStream()
            .filter(p -> !StringUtils.isEmpty(p.getPermission()))
            .map(p -> new SimpleGrantedAuthority(p.getPermission()))
            .collect(Collectors.toSet());
    }

    /**
     * 账户是否未过期
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return getStatus() != Status.LOCKED;
    }

    /**
     * 密码是否未过期
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否激活
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
