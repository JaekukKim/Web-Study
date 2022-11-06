<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 웹사이트.</title>
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
	<!-- 게시판의 글쓰기 화면을 만들어준다. -->
	<div class="container">
		<!-- 특정한 내용을 담을 컨테이너를 지정한다. -->

		<div class="row">
			<form method="post" action="writeAction.jsp">
			<!-- post방식으로 writeAction.jsp파일로 전달한다. -->
			
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">


					<thead>
						<!-- 테이블의 제목 부분이라 할수있다. 각각의 속성들을 알려준다. -->

						<tr>
							<!-- 테이블의 "행"을 나타내는 tr태그이다. 즉 한 줄. -->

							<th colspan="2"
								style="background-color: #eeeeee; text-align: center;">게시판
								글쓰기 양식</th>
							<!-- colspan="n"은 n개만큼의 열을 잡아먹을수 있도록 설정해주는 요소이다. -->
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control"
								placeholder="글 제목" name="bbsTitle" maxlength="50"></td>
						</tr>
						
						<tr>
							<td><textarea class="form-control" placeholder="글 내용"
									name="bbsContent" maxlength="4096" style="height: 350px;"></textarea></td>
						</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="글쓰기">
				<!-- 글쓰기 버튼을 만들어 주고 오른쪽에 고정되게 만들어준다. 클릭하면 글 작성 완료 -->
			</form>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>