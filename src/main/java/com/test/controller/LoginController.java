package com.test.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.service.AuthService;

public class LoginController implements SubController {

	private static String msg;
	private AuthService service = AuthService.getInstance();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {

		try {
			// 0 메시지 처리
			msg = req.getParameter("msg");
			if (msg != null) {
				req.setAttribute("msg", msg);
			}
			// 0 Get이면 페이지 이동
			String method = req.getMethod();
			if (method.equals("GET")) {
				System.out.println("[LC] 요청 Method : " + method);
				req.getRequestDispatcher("/WEB-INF/view/auth/login.jsp").forward(req, resp);
				return;
			}

			// 1 파라미터 확인
			Map<String, String[]> params = req.getParameterMap();

			// 2 유효성 검사
			boolean isvalid = isValid(params);
			if (!isvalid) {
				// 유효성 체크 오류 발생시 전달할 메시지 + 이동될 경로
				req.setAttribute("msg", msg); // msg를 담아주고
				req.getRequestDispatcher("/WEB-INF/view/auth/login.jsp").forward(req, resp); 
				return;
			}

			// 3 서비스 실행
			boolean flag = service.LoginCheck(params, req);
			if(!flag) {
				req.getRequestDispatcher("/WEB-INF/view/auth/login.jsp").forward(req, resp); 
			}
			

			// 4 view 이동
			String path = req.getContextPath();
			resp.sendRedirect(path + "/main.do");
			return ;
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean isValid(Map<String, String[]> params) {

		for (String name : params.keySet()) {
			// 공백확인
			if (params.get(name)[0].isEmpty()) {
				msg = "<i class='bi bi-exclamation-triangle' style='color : orange'></i> <span>공백은 포함할 수 없습니다. </span>";
				return false;
			}
		}
		return true;
	}

}
