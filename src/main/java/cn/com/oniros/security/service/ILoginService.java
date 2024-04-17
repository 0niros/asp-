package cn.com.oniros.security.service;

import cn.com.oniros.security.entity.dto.LoginDto;
import cn.com.oniros.security.entity.vo.LoginVO;
import cn.com.oniros.user.manager.api.dto.AddUserDto;

/**
 * cn.com.oniros.security.service.ILoginService
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:37
 */
public interface ILoginService {

    /**
     *  Do login.
     *
     * @param loginDto login dto.
     * @return LoginVo contains token.
     */
    LoginVO login(LoginDto loginDto);

    /**
     *  Do register user.
     *
     * @param addUserDto user info.
     */
    void addUser(AddUserDto addUserDto);

}
