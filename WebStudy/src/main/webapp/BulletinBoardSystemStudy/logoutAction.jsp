<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹사이트.</title>
</head>
<body>
	<%
		// 로그아웃시 session id를 없애야한다.invalidate(); 메소드는 세션 id를 "빼앗기게"만드는 메소드이다. 
		session.invalidate();
	%>
	<script>
		location.href = 'main.jsp';
		// 로그아웃하면 main페이지로 넘어가게 만든다.
	</script>
</body>
</html>