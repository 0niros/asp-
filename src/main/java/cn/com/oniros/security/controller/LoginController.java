package cn.com.oniros.security.controller;

import cn.com.oniros.http.ApiResult;
import cn.com.oniros.security.entity.dto.LoginDto;
import cn.com.oniros.security.entity.vo.LoginVO;
import cn.com.oniros.security.service.ILoginService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * cn.com.oniros.security.controller.LoginController
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:34
 */
@Validated
@RestController
@RequestMapping("/asp/auth/v1")
public class LoginController {

    @Resource
    private ILoginService loginService;

    @PostMapping("/login")
    public ApiResult<LoginVO> login(@RequestBody @Valid LoginDto loginDto) {
        return new ApiResult<>(loginService.login(loginDto));
    }

    @PostMapping("/checkToken")
    public ApiResult<Void> checkToken() {
        return ApiResult.success(null);
    }

}
