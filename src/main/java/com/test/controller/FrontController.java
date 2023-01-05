package com.test.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {

	private Map<String, SubController> list;

	// init
	@Override
	public void init(ServletConfig config) throws ServletException {
		String path = config.getServletContext().getContextPath();  // /02MVC2 경로 저장 
		System.out.println("[FC] CONTEXT PATH : " + path);
		
		list = new HashMap();
		
		//URL, SubController 저장
		//Member
		list.put(path + "/member/join.do", new MemberJoinController());
		
		//Board
		list.put(path + "/board/list.do", new BoardListController());

		
		//Notice
		list.put(path + "/notice/list.do", new NoticeListController());
		
		//Auth
		list.put(path + "/auth/login.do", new LoginController());
		
		//Main
		list.put(path + "/main.do", new MainController());
		
	}

	// service
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//문자셋 설정(Filter 이동예정)
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		
		//SubController 실행
		String uri = req.getRequestURI();
		System.out.println("[FC] URI : " + uri);
		//uri정보와 일치하는 컨트롤러가 꺼내지게 됨. 그래서 자연스레 업캐스팅이 일어나고 오버라이딩된 메서드 execute가 실행되는거임.
		SubController sub = list.get(uri);
		sub.execute(req, resp);
		
		
		
		
	}

}
