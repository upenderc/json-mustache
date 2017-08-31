package com.rediscache.poc;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class StudentRepositoryImpl implements StudentRepository {

	private static final String KEY = "Student";
    
    private RedisTemplate<String, Student> redisTemplate;
    private HashOperations<String,String,Student> hashOps;
 
    public StudentRepositoryImpl(RedisTemplate<String,Student> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
 
    @PostConstruct
    public void init() {
        hashOps = redisTemplate.opsForHash();
    }
     
    public void saveStudent(Student student) {
        hashOps.put(KEY, student.getId(), student);
    }
 
    public void updateStudent(Student student) {
        hashOps.put(KEY, student.getId(), student);
    }
 
    public Student findStudent(String id) {
        return (Student) hashOps.get(KEY, id);
    }
 
    public Map<String, Student> findAllStudents() {
        return hashOps.entries(KEY);
    }
 
    public void deleteStudent(String id) {
        hashOps.delete(KEY, id);
    }

}
