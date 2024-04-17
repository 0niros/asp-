package cn.com.oniros.security.config;

import cn.com.oniros.security.entity.UserSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * cn.com.oniros.security.config.RedisConfiguration
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:24
 */
@Configuration
public class RedisConfiguration {

    @Bean(name = "authServerRedisTemplate")
    public RedisTemplate<String, UserSecurity> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserSecurity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return template;
    }

}
