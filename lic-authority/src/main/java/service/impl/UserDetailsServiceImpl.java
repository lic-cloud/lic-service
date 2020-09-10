package service.impl;


import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.user.Permission;
import cn.bestsort.model.entity.user.SysRolePermission;
import cn.bestsort.model.entity.user.SysRoleUser;
import cn.bestsort.model.entity.user.User;
import cn.bestsort.model.enums.Status;
import cn.bestsort.repository.PermissionRepository;
import cn.bestsort.repository.SysRolePermissionRepository;
import cn.bestsort.repository.SysRoleUserRepository;
import cn.bestsort.repository.UserRepository;
import dto.LoginUser;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * spring security登陆处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SysRoleUserRepository sysRoleUserRepository;
	@Autowired
	private SysRolePermissionRepository sysRolePermissionRepository;
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection list = new ArrayList();
		User user = userRepository.findByUserName(username).orElseThrow(() -> ExceptionConstant.UNAUTHORIZED);
		if (user.getStatus() == Status.INVALID) {
			throw new LockedException("用户无效,请联系管理员");
		}
		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(user, loginUser);

		List<Long> allByUserId = new ArrayList<>();// = sysRoleUserRepository.findAllByUserId(user.getId());
		List<SysRolePermission> allByIdIn = sysRolePermissionRepository.findAllByIdIn(allByUserId);
		allByIdIn.forEach(str -> {
			list.add(str.getPermissionId());
		});
		List<Permission> allByIdIn1 = permissionRepository.findAllByIdIn(list);
		loginUser.setPermissions(allByIdIn1);
		return loginUser;
	}

}
