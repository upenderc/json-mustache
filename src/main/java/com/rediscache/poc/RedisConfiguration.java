package com.rediscache.poc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
	    return new JedisConnectionFactory();
	}
	 
	@Bean
	public RedisTemplate<String, Student> redisTemplate() {
	    RedisTemplate<String, Student> template = new RedisTemplate<String, Student>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    return template;
	}
	@Bean 
	public StudentRepository studentRepository() {
		StudentRepositoryImpl sr = new StudentRepositoryImpl(redisTemplate());
		sr.init();
		return sr;
	}
}
