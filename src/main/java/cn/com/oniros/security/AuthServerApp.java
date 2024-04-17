package cn.com.oniros.security;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * cn.com.oniros.security.AuthServerApp
 *
 * @author Li Xiaoxu
 * 2024/4/14 23:28
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class AuthServerApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }
}
