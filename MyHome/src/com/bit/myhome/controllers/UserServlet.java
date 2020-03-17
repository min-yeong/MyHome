package com.bit.myhome.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.myhome.dao.UsersDao;
import com.bit.myhome.dao.UsersDaoImpl;
import com.bit.myhome.vo.UserVO;

@WebServlet("/users")
public class UserServlet extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Action parameter를 이용한 조건 분기
		String action = req.getParameter("a");
		
		if("joinform".equals(action)) {
			// a = joinform 일 경우 
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/users/joinform.jsp");
			rd.forward(req, resp);
		} else if("joinsuccess".equals(action)) {
			// a = joinsuccess 일 경우 
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/users/joinsuccess.jsp");
			rd.forward(req, resp);
		} else if("loginform".equals(action)) {
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/users/loginform.jsp");
			rd.forward(req, resp);
		} else if("logout".equals(action)) {
			// 세션을 지웁니다 (무효화)
			HttpSession session = req.getSession();
			session.invalidate();
			resp.sendRedirect(req.getContextPath());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("a");
		if("join".equals(action)) {
			// 가입처리
			String name = req.getParameter("name");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			String gender = req.getParameter("gender");
			
			UserVO vo = new UserVO(name, password, email, gender);
			UsersDao dao = new UsersDaoImpl(dbuser, dbpass);
			
			boolean isSuccess = dao.insert(vo);
			if(isSuccess) {
				System.out.println("INSERT SUCCESS");
				resp.sendRedirect(req.getContextPath()+"/users?a=joinsuccess");
			}
			else {
				System.err.println("INSERT FAILED");
				resp.sendRedirect(req.getContextPath()+"/users?a=joinform");
			}
		}else if("login".equals(action)) {
			//UserVO를 Dao로부터 받아오기
			//UserVO가 null 이 아니면 -> Session에 세팅 -> / , null이면 -> /users?a=loginform으로 리다이렉트
			String email = req.getParameter("email");
			String password =req.getParameter("password");
			UsersDao dao = new UsersDaoImpl(dbuser, dbpass);
			
			UserVO user = dao.getUserByIdAndPassword(email, password);
			if(user !=null) {
				//사용자가 있다 -> 세선 설정
				HttpSession session = req.getSession();
				session.setAttribute("loginUser", user);
				resp.sendRedirect(req.getContextPath());
			} else {
				//사용자가 없다 -> 로그인 폼으로 리다이렉트
				resp.sendRedirect(req.getContextPath()+"/users?a=loginform&result=fail");
			}
		}else resp.sendError(405);
	}
}
