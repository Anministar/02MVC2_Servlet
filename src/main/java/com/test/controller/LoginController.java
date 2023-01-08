package com.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements SubController{

	private static String msg;
	
	
	
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			msg = req.getParameter("msg");
			if(msg != null) {
				req.setAttribute("msg", msg);
			}
			
			
			
			
			
			req.getRequestDispatcher("/WEB-INF/view/auth/login.jsp").forward(req,  resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
