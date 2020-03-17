package com.bit.myhome.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.myhome.dao.UsersDao;
import com.bit.myhome.dao.UsersDaoImpl;
import com.bit.myhome.vo.UserVO;

@WebServlet("/")
public class HomeServlet extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp");
		rd.forward(req, resp);	
	}
}