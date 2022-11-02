<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹사이트.</title>
</head>
<body>
	<%
	// 로그인 한 유저는 로그인이나 회원가입 페이지에 못들어가게 해야한다.
	String userID = null;
	if(session.getAttribute("userID") != null){
		// userID란 이름으로 세션이 존재하는 유저들은
		userID = (String) session.getAttribute("userID");
		// 해당 아이디에 session id를 넣어주도록 한다.
		// 이러면 userID란 변수가 자신에게 할당된 session id로 바뀐다.
	}
	
	// 이미 로그인이 되어있는 유저들은 다시 메인페이지로 돌려보내야한다.
	if (userID != null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 로그인이 되어 있습니다.')");
		script.println("location.href = 'main.jsp'");
		script.println("</script>");
	}
	
	// userdao라는 객체를 생성하여 login.jsp파일에 넘어온 userID와 userPassword를 받아와 그 값을 로그인 메소드에 넣어서 실행해준다.
	UserDAO userDAO = new UserDAO();
	int result = userDAO.login(user.getUserID(), user.getUserPassword());
	// 로그인에 성공했을시 페이지를 이동하는 코드 선언.
	if (result == 1) {
		// 로그인에 성공했을 시 세션 id를 부여한다.
		session.setAttribute("userID", user.getUserID());
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'main.jsp'");
		// 로그인에 성공하면 위 main.jsp로 이동한다!
		script.println("</script>");
		
	} else if (result == 0) { // 비밀번호 틀렸을때.
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('비밀번호가 틀립니다.')");
		script.println("history.back()"); // 이전 페이지로 강제로 돌려보냄.
		script.println("</script>");
		
	} else if (result == -1) { // 아이디가 존재하지 않을 때.
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('아이디가 존재하지 않습니다.')");
		script.println("history.back()"); // 이전 페이지로 강제로 돌려보냄.
		script.println("</script>");
		
	} else if (result == -2) { // 데이터베이스 오류 발생 시.
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('데이터베이스 오류가 발생했습니다.')");
		script.println("history.back()"); // 이전 페이지로 강제로 돌려보냄.
		script.println("</script>");
	}
	%>
</body>
</html>