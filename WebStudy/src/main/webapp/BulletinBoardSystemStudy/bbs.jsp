<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="bbs.BbsDAO"%>
<%@ page import="bbs.Bbs"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판</title>
</head>
<body>
	<%
	// 로그인이 된 사람은 로그인이 된 정보를 담을 수 있게 만들어주어야 한다.
	// 세션이 존재하는 사람이라면 그 id값을 그대로 받아서 관리할수 있게 만들어준다.
	String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
		// 로그인을 한 사람이라면 userid에 해당 아이디가 담기고 아니라면 null값이 담기게 될것이다.
	}

	// 현재 게시판이 몇번째 페이지인지 알려줘야 한다.

	int pageNumber = 1; // 처음은 무조건 1페이지.
	if (request.getParameter("pageNumber") != null) {
		// 파라미터로 페이지 넘버가 넘어 왔다면 페이지 넘버에는 해당 파라미터의 값을 넣어줘야 한다. 웹에서의 파라미터는 무조건 String타입이므로
		// 문자열을 숫자열로 읽을수 있도록 parse + 기본타입 으로 바꿔주어야 한다.
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));

	}
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
	<!-- 게시판의 화면을 넣어주자. 게시판은 글이 작성되면 작성한 순서대로 보여주는 "테이블!!"구조로 작성이 되어있다. 즉 하나의 표 형태이다. 테이블을 만들고 디자인해보자. -->
	<div class="container">
		<!-- 특정한 내용을 담을 컨테이너를 지정한다. -->

		<div class="row">
			<!-- 테이블이 들어갈 공간을 만들어주는 row선언 -->
			<!-- 테이블 자체는 글의 목록을 보여주는 역할밖에 하지 않는다. -->

			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<!-- 게시판의 글 목록이 홀수와 짝수를 번갈아가며 색이 변경되도록 해주는 요소이고 가독성을 높인다. -->

				<thead>
					<!-- 테이블의 제목 부분이라 할수있다. 각각의 속성들을 알려준다. -->

					<tr>
						<!-- 테이블의 "행"을 나타내는 tr태그이다. 즉 한 줄. -->

						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeeee; text-align: center;">작성날짜</th>
					</tr>
				</thead>
				<tbody>
					<%
					// 객체를 생성 후
					BbsDAO bbsDAO = new BbsDAO();

					// 값들은 현재의 페이지에서 가져온 게시글 목록이 된다.
					ArrayList<Bbs> list = bbsDAO.getList(pageNumber);

					// 그리고 그 값들을 하나씩 출력해준다.
					for (int i = 0; i < list.size(); i++) {
					%>
					<tr>
						<!-- 현재 게시글에 대한 정보를 가져올 수 있도록 한다. for문이 적용된 html이다 신기함; -->
						<td><%=list.get(i).getBbsID()%></td>
						<td><a href="view.jsp?bbsID=<%=list.get(i).getBbsID()%>">
								<%=list.get(i).getBbsTitle().replaceAll(" " , "&nbsp;")
						.replaceAll("<", "&lt;").replaceAll(">","&gt;").replaceAll("\n", "<br>")%></a></td>
						<!-- 제목을 눌렀을 시 해당 게시글의 내용으로 이동해야 하기 때문에 하이퍼링크에 해당 게시글의 번호를 매개변수로 보낸다.
						 그럼 해당 게시글에 해당하는 내용의 글이 나올수 있게 한다(해당하는 view 페이지로 이동) -->
						 <!-- 게시글 정보도 리스트에서 불러올 때 특수문자가 포함이 되어 있으면 크로스 사이트 스크립팅이 가능하다. 불러오는 부분도 바꿔주어야 한다. -->
						<td><%=list.get(i).getUserID()%></td>
						<td><%=list.get(i).getBbsDate().substring(0, 11) + list.get(i).getBbsDate().substring(11, 13) + "시"
		+ list.get(i).getBbsDate().substring(14, 16) + "분"%></td>
		<!-- substring 메소드로 날짜의 출력 방식을 바꾼다, 날짜,시,분 만 출력되게. -->
					</tr>
					<%
					}
					%>

				</tbody>
			</table>
			<a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a>
			<!-- 글쓰기 버튼을 만들어 주고 오른쪽에 고정되게 만들어준다. -->
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>