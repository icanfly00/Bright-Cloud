package com.tml.auth.service.impl;

import com.tml.api.system.entity.SysUser;
import com.tml.auth.manager.UserManager;
import com.tml.common.core.entity.BrightAuthUser;
import com.tml.common.core.entity.constant.ParamsConstant;
import com.tml.common.core.entity.constant.SocialConstant;
import com.tml.common.core.utils.BrightUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Service
@RequiredArgsConstructor
public class BrightUserDetailServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest httpServletRequest = BrightUtil.getHttpServletRequest();
        SysUser sysUser = userManager.findByName(username);
        if (sysUser != null) {
            String permissions = userManager.findUserPermissions(sysUser.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(SysUser.STATUS_VALID, sysUser.getStatus())) {
                notLocked = true;
            }
            String password = sysUser.getPassword();
            String loginType = (String) httpServletRequest.getAttribute(ParamsConstant.LOGIN_TYPE);
            if (StringUtils.equals(loginType, SocialConstant.SOCIAL_LOGIN)) {
                password = passwordEncoder.encode(SocialConstant.SOCIAL_LOGIN_PASSWORD);
            }

            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
            if (StringUtils.isNotBlank(permissions)) {
                grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
            }
            BrightAuthUser authUser = new BrightAuthUser(sysUser.getUsername(), password, true, true, true, notLocked,
                    grantedAuthorities);

            BeanUtils.copyProperties(sysUser, authUser);
            return authUser;
        } else {
            throw new UsernameNotFoundException("");
        }
    }
}
