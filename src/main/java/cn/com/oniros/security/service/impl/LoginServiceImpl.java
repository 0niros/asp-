package cn.com.oniros.security.service.impl;

import cn.com.oniros.http.CustomException;
import cn.com.oniros.security.constant.CustomErrorCode;
import cn.com.oniros.security.entity.UserSecurity;
import cn.com.oniros.security.entity.dto.LoginDto;
import cn.com.oniros.security.entity.vo.LoginVO;
import cn.com.oniros.security.jwt.JwtUtils;
import cn.com.oniros.security.service.ILoginService;
import cn.com.oniros.user.manager.api.dto.AddUserDto;
import cn.com.oniros.user.manager.api.provider.IUserManagerApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * cn.com.oniros.security.service.impl.LoginServiceImpl
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:43
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource(name = "authServerRedisTemplate")
    private RedisTemplate<String, UserSecurity> redisTemplate;

    @DubboReference
    private IUserManagerApi userManagerApi;

    @Override
    public LoginVO login(LoginDto loginDto) {
        // 0. Get authenticate.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate == null) {
            throw new CustomException(CustomErrorCode.AUTH_ERROR);
        }

        // 1. Get user info and generate token.
        UserSecurity user = (UserSecurity) authenticate.getPrincipal();
        String token = JwtUtils.createToken(user.getUsername(), null, user.getUser().getAuthorities().get(0));

        // 2. Add token into redis.
        redisTemplate.opsForValue().set(JwtUtils.generateJwtCacheKey(user.getUsername()),
                user, JwtUtils.JWT_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return new LoginVO(token);
    }

    @Override
    public void addUser(AddUserDto addUserDto) {
        userManagerApi.addUser(addUserDto);
    }
}
