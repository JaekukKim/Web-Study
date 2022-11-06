<%@page import="bbs.Bbs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO"%>
<%@ page import="java.io.PrintWriter"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />

<!-- 로그인할때는 아이디와 패스워드만 받으면 됬지만 회원가입은 모든 정보를 다 받아야하므로 모든 정보를 받는 태그를 만들어줘야만한다. -->
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹사이트.</title>
</head>
<body>
	<%
	// 게시글을 작성할라면 로그인이 되어 있고 세션 아이디를 받아와야만한다.
	String userID = null;
	if (session.getAttribute("userID") != null) {
		// userID란 이름으로 세션이 존재하는 유저들은
		userID = (String) session.getAttribute("userID");
		// 해당 아이디에 session id를 넣어주도록 한다.
		// 이러면 userID란 변수가 자신에게 할당된 session id로 바뀐다.
	}

	// 글쓰기 기능 같은경우 로그인이 되어 있어야만 하는 상황에나 가능하다.
	if (userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인이 필요합니다.')");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
	} else {
		// 게시글에 관련된 내용이 하나라도 빠져있다면 작성이 좀 어렵다.
		if (bbs.getBbsTitle() == null || bbs.getBbsContent() == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 존재합니다.')");
			script.println("history.back()");
			script.println("</script>");

		} else {
			BbsDAO bbsDAO = new BbsDAO();
			int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());
			// 입력받은 값들은 이상이 없다면 위 user에 대입되어 데이터베이스로 넘어간다.

			if (result == -1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('글쓰기에 실패했습니다 (데이터베이스 오류)')");
		script.println("history.back()");
		script.println("</script>");

			} else {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");

			}
		}
	}
	%>
</body>
</html>