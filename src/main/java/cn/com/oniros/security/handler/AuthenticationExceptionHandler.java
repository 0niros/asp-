package cn.com.oniros.security.handler;

import cn.com.oniros.http.ApiResult;
import cn.com.oniros.security.constant.CustomErrorCode;
import cn.com.oniros.security.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * cn.com.oniros.security.handler.AuthenticationExceptionHandler
 *
 * @author Li Xiaoxu
 * 2024/4/14 15:46
 */
@Slf4j
@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        fallback(response);
    }

    private void fallback(HttpServletResponse response) {
        // 1. Set response header.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 2. Write into response.
        try (PrintWriter writer = response.getWriter()) {
            ApiResult<Void> result = new ApiResult<>(CustomErrorCode.AUTH_ERROR);
            writer.write(JsonUtils.serialize(result));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
