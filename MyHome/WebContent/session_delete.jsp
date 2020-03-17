<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세션삭제</title>
</head>
<body>
<%
session.invalidate();
// 세션무효
%>
<p>세션을 삭제했습니다</p>
<a href="session_read.jsp">세션읽기</a>
<a href="session_write.jsp">세션저장</a>
</body>
</html>