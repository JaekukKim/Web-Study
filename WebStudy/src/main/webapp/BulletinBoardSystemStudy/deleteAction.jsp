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
	// 글 삭제를 구동시키는 페이지를 만든다.
	
	String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	} // 글 삭제도 본인만 가능하게 만들어야 하기 때문에 sessionID값이 일치하는지를 봐야한다.

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
		// 이렇게 권한을 설정 해 주면 쿼리문(bbsID)에 글 번호를 입력하고 들어가도 권한이 없다고 알림이 뜬다. 
	} else {
		// 삭제하는 과정에 글 내용을 검증할 필요는 없다 글 내용에서 전부 본다음에 판단 할 수 있기 때문. 

			BbsDAO bbsDAO = new BbsDAO();
			int result = bbsDAO.delete(bbsID);
			// delete 메소드는 bbsID만 받아오게 만들었으므로 나머지는 다 지워준다.

			if (result == -1) { // 데이터베이스 오류일시 출력문구.
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('글삭제에 실패했습니다 (데이터베이스 오류)')");
		script.println("history.back()");
		script.println("</script>");

			} else { // 글 삭제가 성공되면 다시 게시판 메인으로 이동.
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");

			}
		}
	
	%>
</body>
</html>