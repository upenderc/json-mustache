package com.rediscache.poc;

import java.util.Map;

public interface StudentRepository {
    void saveStudent(Student person);
    void updateStudent(Student person);
    Student findStudent(String id);
    Map<String, Student> findAllStudents();
    void deleteStudent(String id);
	
}
