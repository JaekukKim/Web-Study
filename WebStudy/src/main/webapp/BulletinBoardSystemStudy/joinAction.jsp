<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="user" class="user.User" scope="page" />

<!-- 로그인할때는 아이디와 패스워드만 받으면 됬지만 회원가입은 모든 정보를 다 받아야하므로 모든 정보를 받는 태그를 만들어줘야만한다. -->
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹사이트.</title>
</head>
<body>
	<%
	// 마찬가지로 회원가입 페이지도 이렇게 만들어주어야 한다.
	String userID = null;
	if (session.getAttribute("userID") != null) {
		// userID란 이름으로 세션이 존재하는 유저들은
		userID = (String) session.getAttribute("userID");
		// 해당 아이디에 session id를 넣어주도록 한다.
		// 이러면 userID란 변수가 자신에게 할당된 session id로 바뀐다.
	}

	// 이미 로그인이 되어있는 유저들은 다시 메인페이지로 돌려보내야한다.
	if (userID != null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 로그인이 되어 있습니다.')");
		script.println("location.href = 'main.jsp'");
		script.println("</script>");
	}

	// 아무것도 입력하지 않고 확인버튼을 누를경우. (모든 값이 null값일때나 하나라도 null값일때를 처리해 주어야 한다.)
	if (user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null
			|| user.getUserGender() == null || user.getUserEmail() == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안된 사항이 존재합니다.')");
		script.println("history.back()");
		script.println("</script>");

	} else {
		UserDAO userDAO = new UserDAO();
		int result = userDAO.join(user);
		// 입력받은 값들은 이상이 없다면 위 user에 대입되어 데이터베이스로 넘어간다.

		if (result == -1) {
			// -1은 데이터베이스 오류다, id는 PRIMARY KEY로 저장되어 오로지 1개씩만 관리되기 때문에 아이디가 중복되면 db오류로 -1이 리턴된다.
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 존재하는 아이디입니다.')");
			script.println("history.back()");
			script.println("</script>");

		} else { // 회원가입이 완료되면 메인페이지로 이동. (INSERT문을 사용하였기 때문에 -1을 제외한 리턴값(양수)은 전부 회원가입이 된 상태이다.)

			session.setAttribute("userID", user.getUserID());
			// 마찬가지로 회원가입에 성공했을 때도 세션 id를 부여한다.
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");

		}
	}
	%>
</body>
</html>