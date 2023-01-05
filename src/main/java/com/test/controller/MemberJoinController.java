package com.test.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.dao.MemberDao;

public class MemberJoinController implements SubController {

	private static String msg;

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		// System.out.println("[MJC] Start-");

		try {
			// 0 Get 구별
			String method = req.getMethod();
			if (method.equals("GET")) {
				System.out.println("[MJC] 요청 Method : " + method);
				req.getRequestDispatcher("/WEB-INF/view/member/join.jsp").forward(req, resp);
				return;
			}

			// 1 파라미터
			Map<String, String[]> params = req.getParameterMap();
			// 파라미터 확인
//			for (String name : params.keySet()) {
//				System.out.println("name : " + name + " val : " + params.get(name)[0]);
//			}

			// 2 Validation
			boolean isvalid = isValid(params);
			if (!isvalid) {
				// 유효성 체크 오류 발생시 전달할 메시지 + 이동될 경로

				req.setAttribute("msg", msg) ; // msg를 담아주고
				req.getRequestDispatcher("/WEB-INF/view/member/join.jsp").forward(req, resp); // 다시 join.jsp로 넘어가는것임.
				return;
			}
			
			// 3 Service
			
			MemberDao dao = MemberDao.getInstance();
			dao.Insert(null);

			// 4 View(로그인페이지로 이동)
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean isValid(Map<String, String[]> params) {
		
		for (String name : params.keySet()) {
			//공백확인
			if(params.get(name)[0].isEmpty()) {
				msg = "<i class='bi bi-exclamation-triangle' style='color : orange'></i> <span>공백은 포함할 수 없습니다. </span>";
				return false;
			}
			if(name.equals("pwd")) {
				String pwvalue = params.get(name)[0];	//입력된 패스워드 값 가져오기
				 // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
				  Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
				  Matcher passMatcher1 = passPattern1.matcher(pwvalue);
				  
				  if(!passMatcher1.find()){
					  msg = "<i class='bi bi-exclamation-triangle' style='color : orange'></i> <span>패스워드는 영소문자/대문자/특수문자/숫자를 포함하며 8자 이상이어야 합니다. </span>";
				    return false;
				  }
			}
			
			
		}

		
		return false;
	}

}
