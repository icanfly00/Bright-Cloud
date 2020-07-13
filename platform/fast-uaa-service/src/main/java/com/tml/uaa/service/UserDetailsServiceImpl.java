package com.tml.uaa.service;

import cn.hutool.core.util.ObjectUtil;
import com.tml.common.api.CommonResult;
import com.tml.common.constant.CommonConstant;
import com.tml.common.entity.LoginType;
import com.tml.common.entity.RestUserDetails;
import com.tml.system.entity.SysUser;
import com.tml.uaa.service.feign.SysUserFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

;

/**
 * @Description com.tml.security.service
 * @Author TuMingLong
 * @Date 2020/5/19 17:23
 */
@Slf4j
@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserFeignService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonResult<SysUser> commonResult = userService.findSecurityUserByUsername(username);
        System.out.println(commonResult);
        SysUser account = null;
        if (commonResult.getCode() == 200) {
            account = commonResult.getData();
        }
        if (ObjectUtil.isNull(account)) {
            log.error("登录用户：" + username + " 不存在.");
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        Collection<? extends GrantedAuthority> authorities = getUserAuthorities(account.getUserId());

        Integer userId = account.getUserId();
        String password = account.getPassword();
        String avatar = account.getAvatar();
        String phone = account.getPhone();
        boolean accountNonLocked = Integer.valueOf(account.getLockFlag()) != CommonConstant.ACCOUNT_STATUS_LOCKED;
        boolean credentialsNonExpired = true;
        boolean enabled = Integer.valueOf(account.getLockFlag()) == CommonConstant.ACCOUNT_STATUS_NORMAL ? true : false;
        boolean accountNonExpired = true;
        RestUserDetails userDetails = new RestUserDetails();
        userDetails.setUserId(userId);
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        userDetails.setAuthorities(authorities);
        userDetails.setAccountNonExpired(accountNonExpired);
        userDetails.setAccountNonLocked(accountNonLocked);
        userDetails.setCredentialsNonExpired(credentialsNonExpired);
        userDetails.setEnabled(enabled);
        userDetails.setLoginType(LoginType.normal);
        userDetails.setAvatar(avatar);
        userDetails.setPhone(phone);
        userDetails.setEnterpriseId(account.getEnterpriseId());
        userDetails.setEnterpriseIdList(getUserDataPerms(userId));
        return userDetails;
    }


    /**
     * 手机验证码登录
     *
     * @param mobile
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setPhone(mobile);
        //  通过手机号mobile去数据库里查找用户以及用户权限

        CommonResult<SysUser> commonResult = userService.findSecurityUserByPhone(mobile);
        SysUser account = null;
        if (commonResult.getCode() == 200) {
            account = commonResult.getData();
        }
        if (ObjectUtil.isNull(account)) {
            log.error("登录手机号：" + mobile + " 不存在.");
            throw new UsernameNotFoundException("登录手机号：" + mobile + " 不存在");
        }
        // 获取用户拥有的角色
        Collection<? extends GrantedAuthority> authorities = getUserAuthorities(account.getUserId());
        Integer userId = account.getUserId();
        String username = account.getUsername();
        String password = account.getPassword();
        String avatar = account.getAvatar();
        String phone = account.getPhone();
        boolean accountNonLocked = Integer.valueOf(account.getLockFlag()) != CommonConstant.ACCOUNT_STATUS_LOCKED;
        boolean credentialsNonExpired = true;
        boolean enabled = Integer.valueOf(account.getLockFlag()) == CommonConstant.ACCOUNT_STATUS_NORMAL ? true : false;
        boolean accountNonExpired = true;
        RestUserDetails userDetails = new RestUserDetails();
        userDetails.setUserId(userId);
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        userDetails.setAuthorities(authorities);
        userDetails.setAccountNonExpired(accountNonExpired);
        userDetails.setAccountNonLocked(accountNonLocked);
        userDetails.setCredentialsNonExpired(credentialsNonExpired);
        userDetails.setEnabled(enabled);
        userDetails.setLoginType(LoginType.sms);
        userDetails.setAvatar(avatar);
        userDetails.setPhone(phone);
        userDetails.setEnterpriseId(account.getEnterpriseId());
        userDetails.setEnterpriseIdList(getUserDataPerms(userId));
        return userDetails;
    }


    /**
     * 封装 根据用户Id获取权限
     *
     * @param userId
     * @return
     */
    private Collection<? extends GrantedAuthority> getUserAuthorities(int userId) {
        // 获取用户拥有的角色
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        // 权限集合
        Set<String> permissions = new HashSet<>();
        CommonResult<Set<String>> commonResult = userService.findPermsByUserId(userId);
        if (commonResult.getCode() == 200) {
            permissions = commonResult.getData();
        }
        // 角色集合
        Set<String> roleIds = new HashSet<>();
        CommonResult<Set<String>> commonResult2 = userService.findRoleIdByUserId(userId);
        if (commonResult2.getCode() == 200) {
            roleIds = commonResult2.getData();
        }
        permissions.addAll(roleIds);
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions.toArray(new String[0]));
        return authorities;
    }


    /**
     * 封装 根据用户Id获取数据权限
     *
     * @param userId
     * @return
     */
    private String getUserDataPerms(int userId) {
        String dataPerms = null;
        CommonResult<String> commonResult = userService.findDataPermsByUserId(userId);
        if (commonResult.getCode() == 200) {
            dataPerms = commonResult.getData();
        }
        return dataPerms;
    }
}
