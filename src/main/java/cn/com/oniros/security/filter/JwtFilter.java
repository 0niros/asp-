package cn.com.oniros.security.filter;

import cn.com.oniros.http.ApiResult;
import cn.com.oniros.security.config.PermitPathConfig;
import cn.com.oniros.security.constant.CustomErrorCode;
import cn.com.oniros.security.entity.UserSecurity;
import cn.com.oniros.security.jwt.JwtUtils;
import cn.com.oniros.security.util.JsonUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * cn.com.oniros.security.filter.JwtFilter
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:00
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Resource
    PermitPathConfig permitPathConfig;

    @Resource(name = "authServerRedisTemplate")
    RedisTemplate<String, UserSecurity> redisTemplate;

    private static final AntPathMatcher MATCHER = new AntPathMatcher();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 0. Whether uri permitted.
        if (isPermit(request)) {
            filterChain.doFilter(request, response);
        }

        // 1. Get claims and parse JWT.
        String token = request.getHeader("Authorization");
        Claims claims;
        try {
            claims = JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fallback(response);
            return ;
        }

        // 2. Get user info.
        String username = claims.getSubject();
        String cacheKey = JwtUtils.generateJwtCacheKey(username);
        UserSecurity userSecurity = redisTemplate.opsForValue().get(cacheKey);

        // 3. Deal with user info and put into context.
        if (Objects.isNull(userSecurity)) {
            fallback(response);
            return ;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 4. After that, go on filtering.
        filterChain.doFilter(request, response);
    }

    private void fallback(HttpServletResponse response) throws IOException {
        // 1. Set response header.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 2. Write into response.
        try (PrintWriter writer = response.getWriter()) {
            ApiResult<Void> result = new ApiResult<>(CustomErrorCode.UNKNOWN_TOKEN);
            writer.write(JsonUtils.serialize(result));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean isPermit(HttpServletRequest request) {
        String filterPath = request.getServletPath();

        for (String path : permitPathConfig.getPermittedPath()) {
            if (MATCHER.match(filterPath, path)) {
                return true;
            }
        }

        return false;
    }

}
