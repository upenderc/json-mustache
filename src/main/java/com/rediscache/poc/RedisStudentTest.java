package com.rediscache.poc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RedisStudentTest {

	public static void main(String ...args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(RedisConfiguration.class);
		StudentRepository studentRepository = ctx.getBean(StudentRepository.class);
		Student student = new Student(
				  "Eng2015001", "John Doe", Student.Gender.MALE, 1);
				studentRepository.saveStudent(student);
		studentRepository.saveStudent(student);
		Student student1 = print(studentRepository, "Eng2015001");
		student1.setName("Upender Chinthala");
		student1.setId("Eng2015002");
		studentRepository.saveStudent(student1);
		//studentRepository.updateStudent(student1);
		//Student student2 = print(studentRepository, "Eng2015002");
		studentRepository.deleteStudent("Eng2015002");
		for (Student s:studentRepository.findAllStudents().values()) {
			System.out.println(s);
		}
		
		
	}

	private static Student print(StudentRepository studentRepository,String  studentId) {
		Student student = studentRepository.findStudent(studentId);
		System.out.println(student.getName());
		return student;
	}
}
