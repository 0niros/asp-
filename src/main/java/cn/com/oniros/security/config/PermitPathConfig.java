package cn.com.oniros.security.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * cn.com.oniros.security.config.PermitPathConfig
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:03
 */
@Data
@Configuration
@RefreshScope
public class PermitPathConfig {

    @Value("${spring.security.permittedPath:/login}")
    private List<String> permittedPath;

}
