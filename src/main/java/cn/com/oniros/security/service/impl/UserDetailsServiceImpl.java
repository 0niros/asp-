package cn.com.oniros.security.service.impl;

import cn.com.oniros.http.CustomException;
import cn.com.oniros.security.constant.CustomErrorCode;
import cn.com.oniros.security.entity.UserSecurity;
import cn.com.oniros.user.manager.api.dto.UserInfoDto;
import cn.com.oniros.user.manager.api.provider.IUserManagerApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * cn.com.oniros.security.service.impl.UserDetailsServiceImpl
 *
 * @author Li Xiaoxu
 * 2024/4/14 22:51
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @DubboReference
    private IUserManagerApi userManagerApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoDto userInfoDto = userManagerApi.getUserByUsername(username);
        if (userInfoDto == null) {
            log.error("[UserDetail] find user info via rpc for {} error.", username);
            throw new CustomException(CustomErrorCode.AUTH_ERROR);
        }

        return new UserSecurity(userInfoDto);
    }
}
