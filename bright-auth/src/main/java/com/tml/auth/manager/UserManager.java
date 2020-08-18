package com.tml.auth.manager;

import com.tml.api.system.entity.SysUser;
import com.tml.api.system.entity.SysUserDataPermission;
import com.tml.auth.feign.RemoteUserFeignService;
import com.tml.common.core.entity.ResultBody;
import com.tml.common.core.entity.constant.StringConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户业务逻辑
 *
 * @Author TuMingLong
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserManager {

    private final RemoteUserFeignService remoteUserFeignService;

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户
     */
    public SysUser findByName(String username) {
        SysUser user = null;
        ResultBody<SysUser> resultBody=remoteUserFeignService.findByName(username);
        if (resultBody.getCode()==200 && resultBody.getData() != null) {
            user=resultBody.getData();
            ResultBody<List<SysUserDataPermission>> listResultBody=remoteUserFeignService.findUserDataPermissions(user.getUserId());
            if(resultBody.getCode()==200 && resultBody.getData() != null){
                List<SysUserDataPermission> permissions = listResultBody.getData();
                String deptIds = permissions.stream().map(p -> String.valueOf(p.getDeptId())).collect(Collectors.joining(StringConstant.COMMA));
                user.setDeptIds(deptIds);
            }
        }
        return user;
    }

    /**
     * 通过用户名查询用户权限串
     *
     * @param username 用户名
     * @return 权限
     */
    public String findUserPermissions(String username) {
        ResultBody<String> resultBody = remoteUserFeignService.findUserPermissions(username);
        if(resultBody.getCode()==200 && StringUtils.isNoneBlank(resultBody.getData())){
            return resultBody.getData();
        }
        return null;

    }

    /**
     * 注册用户
     *
     * @param username username
     * @param password password
     * @return SysUser
     */
    @Transactional(rollbackFor = Exception.class)
    public SysUser registerUser(String username, String password) {
        ResultBody<SysUser> resultBody=remoteUserFeignService.registerUser(username,password);
        if(resultBody.getCode()==200){
            return resultBody.getData();
        }
        return null;
    }
}
