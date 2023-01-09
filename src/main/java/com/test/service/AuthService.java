package com.test.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.bcrypt.BCrypt;

import com.test.dao.MemberDao;
import com.test.dto.AuthDto;
import com.test.dto.MemberDto;

public class AuthService {
	
	MemberDao dao = MemberDao.getInstance();
	BCrypt bc = new BCrypt();

	
	//싱글톤
	private static AuthService instance;
	public static AuthService getInstance() {
		if(instance == null) {
			instance = new AuthService();
		}
		return instance;
	}
	
	public AuthService() {
		
	}
	
	//Login 확인
	public boolean LoginCheck(Map<String, String[]>parmas, HttpServletRequest request) {
		boolean flag = false;

		
		String email = parmas.get("email")[0];
		String pwd = parmas.get("pwd")[0];
		
		MemberDto mdto = dao.Select(email);
		if(mdto != null) {	//ID 일치 여부확인(DB)
			if(bc.checkpw(pwd, mdto.getPwd())) { //PW 일치 여부확인(DB), bc.checkpw == true, false로 반환
				
				//ID/PW일치한다면 email/grade(권한) 을 Session에 저장
				//Session에는 많은 내용을 저장하면 부하가 생기기 때문에 이정도만 저장, Session을 사용하려면 HttpServletRequest 내장 객체를 받아야하기 때문에 매개변수를 저렇게 잡았음.
				AuthDto adto = new AuthDto();
				adto.setEmail(email);
				adto.setGrade(mdto.getGrade());
				
				
				
				//Session 유지시간 설정	
				HttpSession session = request.getSession();
				session.setAttribute("authdto", adto);
				session.setMaxInactiveInterval(60*5);
				
				//true 전달
				flag = true;
			}
		}
		
		
		
		return flag;
	}
	
	//Logout 처리
	
	
	
	
	
	
//	public static void main(String[] args) {
//		Map<String, String[]> a = new HashMap<String, String[]>();
//		String[] b = new String[] {"test@test.com"};
//		a.put("email", b);
//		System.out.println(a.get("email"));
//		
//		System.out.println(a.get("email")[0]);
//	}
	
	
			
}
