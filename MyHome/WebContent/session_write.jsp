<%@page import="com.bit.myhome.vo.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page session="true" %>
 <!-- JSP 내에서 session 사용하려면 page session="true" 지정 -->
<% 
 session.setAttribute("strVar", "문자열 세선");
 session.setAttribute("intVar", 2019);
 UserVO user = new UserVO("홍길동", "1234", "hong@hwalbin.org", "M");
 session.setAttribute("user", user);
 session.setMaxInactiveInterval(2 * 60 * 90); //2시간
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세션저장</title>
</head>
<body>
	<p>세션이 저장되었습니다</p>
	<a href="session_read.jsp">세션확인</a>
</body>
</html>