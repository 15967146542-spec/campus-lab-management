package com.itzhy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

/**
 * Redis 缓存序列化配置。
 *
 * <p>Spring Data Redis 默认使用 JDK 序列化（JdkSerializationRedisSerializer），
 * 要求被缓存对象实现 {@link java.io.Serializable}，否则写入缓存会抛
 * NotSerializableException；且存入 Redis 的值是二进制、不可读。
 *
 * <p>这里改用 Spring Boot 4 / Jackson 3 的 {@link GenericJacksonJsonRedisSerializer}，
 * 以 JSON 形式存储缓存值（原生支持 java.time），Redis 中可直接查看内容。
 */
@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 反序列化类型白名单：本项目实体 + JDK 集合/时间类型
        PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.itzhy.pojo")
                .allowIfSubType("java.util")
                .allowIfSubType("java.time")
                .build();

        GenericJacksonJsonRedisSerializer valueSerializer = GenericJacksonJsonRedisSerializer.builder()
                .enableSpringCacheNullValueSupport()
                .enableDefaultTyping(validator)
                .build();

        // key 仍用默认 StringRedisSerializer（保证 labList::xxx 形式的可读 key），只覆盖 value 序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(valueSerializer));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
