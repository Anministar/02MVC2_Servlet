package com.test.dto;

public class AuthDto {
	
	private String email;
	private String grade;
	
	//default 생성자
	public AuthDto() {
		// TODO Auto-generated constructor stub
	}
	//getter and setter

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		this.email = mail;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	
	//toString	
	@Override
	public String toString() {
		return "AuthDto [mail=" + email + ", grade=" + grade + "]";
	}
	

	
	

}
