<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="bbs.Bbs"%>
<%@ page import="bbs.BbsDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 글 보기</title>
</head>
<body>
	<%
	String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");

	}

	/* 게시글을 확인할 때 작성자의 아이디를 받아와 만약에 그 아이디가 존재하는 과정을 확인하는 코드이다. 만약 아이디가 존재하면 그냥 아이디를 받아 리턴해준다. */
	int bbsID = 0;
	if (request.getParameter("bbsID") != null) {
		bbsID = Integer.parseInt(request.getParameter("bbsID"));
	}

	// 만약 아이디가 존재하지 않는다면 존재하지 않음을 명시해 주어야한다. 그렇지 않으면 비회원도 게시글을 확인할수가 있기 때문이다.
	if (bbsID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
	}
	// 확인이 되었으면 게시글의 내용을 이제 가져와야한다. BbsDAO 객체에 담겨져 있던 아이디를 가져와 확인시켜주면 된다.
	Bbs bbs = new BbsDAO().getBbs(bbsID);
	%>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹 사이트</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인페이지</a></li>
				<li class="active"><a href="bbs.jsp">게시판</a></li>
			</ul>
			<%
			// 접속하기 부분을 바꿔줄 필요가 있다 왜? 로그인이 되어 있기 때문. 그래서 로그인이 되어있지 않은 경우에만 보이도록 한다.
			if (userID == null) {
			%>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">접속하기 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul></li>
			</ul>

			<%
			} else {// 자바코드 작성 태그는 이런식으로도 활용이 가능하다. 연결되어 있는 중괄호 안에 html언어가 들어가있다!!
			%>
			<!-- 이 안에는 로그인이 되어 있는 사람만 보이는 페이지를 만든다. -->
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">회원정보관리 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
						<!-- 로그인이 되어있는 상태에서는 다른게 보일 필요가 없고 정보관리랑, 로그아웃만 보여지면 된다. -->
					</ul></li>
			</ul>

			<%
			}
			%>

		</div>
	</nav>
	<!-- 이제 글의 내용을 보여주는 페이지를 만들어야한다.. -->
	<div class="container">
		<!-- 특정한 내용을 담을 컨테이너를 지정한다. -->

		<div class="row">

			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">


				<thead>

					<tr>
						<!-- 테이블의 "행"을 나타내는 tr태그이다. 즉 한 줄. -->

						<th colspan="3"
							style="background-color: #eeeeee; text-align: center;">게시판 글
							보기</th>
						<!-- colspan="n"은 n개만큼의 열을 잡아먹을수 있도록 설정해주는 요소이다. -->
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 15%;">글 제목</td>
						<td colspan="3"><%=bbs.getBbsTitle().replaceAll(" " , "&nbsp;")
						.replaceAll("<", "&lt;").replaceAll(">","&gt;").replaceAll("\n", "<br>")%></td>
						<!-- ** 크로스 사이트 스크립팅이랑 해킹 기법을 방어를 해주어야한다. 특수문자 처리를 위의 코드와 같이 해주지 않는다면 HTML태그가 그대로 적용이 되어
						크로스 사이트 스크립팅 해킹에 매우 취약해진다, 크로스 사이트 스크립팅이란 특수문자 처리를 안해준 웹 페이지에 스크립트 코드를 작성하여 그 코드를
						실행시키는 해킹 기법이다. -->
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan="3"><%=bbs.getUserID()%></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="3"><%=bbs.getBbsDate().substring(0, 11) + bbs.getBbsDate().substring(11, 13) + "시"
		+ bbs.getBbsDate().substring(14, 16) + "분"%></td>
					</tr>
					<tr>
						<td>내용</td>
						<td rowspan="4" style="height: 200px; text-align: left;"><%=bbs.getBbsContent().replaceAll(" " , "&nbsp;")
						.replaceAll("<", "&lt;").replaceAll(">","&gt;").replaceAll("\n", "<br>")%></td>
						<!-- 위 대로만 한다면 데이터베이스는 html의 특수문자인지, 아니면 이걸 진짜 문자로 사용하기 위한 용도인지 구분을 못한다.
						그래서 우리는 이걸 지정해 정상적으로 출력될 수 있도록 지정해 주어야 한다. 해결하지 않으면 등록한 글과 다르게 출력된다.
						replaceAll()메소드를 사용 할 예정이다. [특수문자 처리 과정]-->
					</tr>
				</tbody>
			</table>
			<a href="bbs.jsp" class="btn btn-primary">목록</a>
			
			<!-- 작성자가 본인이라면 해당 글을 당연히 수정 할 수 있어야한다. -->
			<%
			if (userID != null && userID.equals(bbs.getUserID())) {
			%>
			<a href="update.jsp?bbsID=<%=bbsID%>" class ="btn btn-primary">수정</a>
			<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="deleteAction.jsp?bbsID=<%= bbsID %>" class ="btn btn-primary">삭제</a>
			<%
			}
			%>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>