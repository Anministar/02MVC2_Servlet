package com.test.controller;

import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.dto.MemberDto;
import com.test.service.MemberService;

public class MemberJoinController implements SubController {

	private static String msg;
	
	private MemberService service = MemberService.getInstance();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
//		 System.out.println("[MJC] Start-");

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
				req.setAttribute("msg", msg); // msg를 담아주고
				req.getRequestDispatcher("/WEB-INF/view/member/join.jsp").forward(req, resp); // 다시 join.jsp로 넘어가는것임.
				return ;
			}
			
			
			// 3 Service
//			MemberDao dao = MemberDao.getInstance();
//			dao.Insert(null);
			
			
			MemberDto dto = new MemberDto();
			// MemberJoinController에서 파라미터 값들을 받아와서 Service로 넘기는 과정
			dto.setEmail(params.get("email")[0]);
			dto.setPwd(params.get("pwd")[0]);
			dto.setPhone(params.get("phone")[0]);
			dto.setZipcode(params.get("zipcode")[0]);
			dto.setAddr1(params.get("addr1")[0]);
			dto.setAddr2(params.get("addr2")[0]);
			// Service로 넘겨서 Service에서 암호화를 거치는 과정
			boolean result = service.memberJoin(dto);
			

			// 4 View(로그인페이지로 이동)
			
			if(result) {
				// 로그인 페이지로 이동
				msg = URLEncoder.encode("회원가입 성공하셨습니다.");
				
				req.setAttribute("msg", msg);
				// Redirect 방식으로 해야 URI가 바뀜. req에 담겨있는 내용을 비우는 과정에서 msg를 전달함으로써 req에 담겨있는 내용을 비움.
				String path = req.getContextPath();
				resp.sendRedirect(path + "/auth/login.do?msg=" + msg);
				
				return ;
				
			} else {
				//회원가입 페이지로 이동
				msg="<i class='bi bi-exclamation-triangle' style='color:orange'></i><span>회원가입 양식에 맞게 다시 입력하세요.</span>";
				req.setAttribute("msg", msg);
				req.getRequestDispatcher("/WEB-INF/view/member/join.jsp").forward(req, resp);
				return ;
			}
			
			

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
			//패스워드 복잡성 체크
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
		return true;
		// isValid 유효성 검사에서 예외가 발생하지 않았는데 false값을 return해서 MemberDao 객체를 만들어서 Insert메서드 동작 확인을 못했음(Console에 아무것도 안뜸.)
		// false, false, true 패턴 이런거 조금 알고 있으면 나중에 Spring할 때 도움이 됨. Spring에서는 이런 패턴을 많이 사용.
	}

}
