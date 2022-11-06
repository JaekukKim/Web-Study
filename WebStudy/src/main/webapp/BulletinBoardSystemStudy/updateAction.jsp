<%@page import="bbs.Bbs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO"%>
<%@ page import="bbs.Bbs"%>
<%@ page import="java.io.PrintWriter"%>
<%
request.setCharacterEncoding("UTF-8");
%>
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

	if (userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인이 필요합니다.')");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
	}

	int bbsID = 0;
	if (request.getParameter("bbsID") != null) {
		bbsID = Integer.parseInt(request.getParameter("bbsID"));
	}

	// 아래 부분은 update.jsp에 있는 내용과 매우 흡사하다.
	// 만약 로그인이 안됬거나 다른 아이디라면 경고창을 출력.
	if (bbsID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
	}

	Bbs bbs = new BbsDAO().getBbs(bbsID);

	// 실제로 글을 작성한 유저가 맞는지를 봐야함, 유저에게 들어간 sessionID에 유저의 원래 아이디를 비교.
	if (!userID.equals(bbs.getUserID())) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
	} else {
		// update.jsp 페이지에서 넘어온 bbsTitle과 bbsContent를 분석해야한다. java beans사용을 하지 않아 request.getparameter로 받아준다.
		// 글의 내용이 작성이 안된 부분이 있다면 작성할수 있게 하도록 코딩을 해 준다.
		if (request.getParameter("bbsTitle") == null || request.getParameter("bbsContent") == null
		|| request.getParameter("bbsTitle").equals("") || request.getParameter("bbsContent").equals("")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된 사항이 존재합니다.')");
			script.println("history.back()");
			script.println("</script>");

		} else {

			// 이제 글번호, 제목, 내용을 받아와 최종적으로 글이 수정될 수 있게 만든다.
			BbsDAO bbsDAO = new BbsDAO();
			int result = bbsDAO.update(bbsID, request.getParameter("bbsTitle"), request.getParameter("bbsContent"));

			if (result == -1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('글수정에 실패했습니다 (데이터베이스 오류)')");
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