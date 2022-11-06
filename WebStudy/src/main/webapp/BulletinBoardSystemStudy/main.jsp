<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/myCSS.css">
<title>JSP 게시판 메인 페이지</title>
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
				<li class="active"><a href="main.jsp">메인페이지</a></li>
				<li><a href="bbs.jsp">게시판</a></li>
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
	<!-- 캐러셀 부분을 만들어 웹사이트를 꾸며주자 -->
		<div class="container">
			<!-- 부트스트랩에서 제공하는 jumbotron을 이용하여 웹 사이트를 꾸며보자. -->
			<div class="jumbotron">
				<div class="container">
					<h1>웹 사이트 소개</h1>
					<p>유튜브 동빈나 님의 강의를 무작정따라하여 만든 JSP웹 페이지 입니다. 이 페이지를 만든 코드들은 전부 탑다운 예정이며 사이트 제작이 완료된 날은 2022-11-06입니다.
					추가로 댓글기능 구현 및 파일 업로드 기능 추가가 될 예정입니다.</p>

					<p>
						<a class="btn btn-primary btn-pul" href="https://github.com/JaekukKim?tab=repositories" role="button">Github</a>
					</p>

				</div>
			</div>
		</div>
		<!-- myCarousel을 이용하여 웹사이트를 꾸미는 과정 -->
		<div>
			<div id = "myCarousel" class="carousel slide" data-ride="carousel">
				<ol class = "carousel-indicators">
					<li data-target = "#myCarousel" data-slide-to = "0" class = "active"></li>
					<li data-target = "#myCarousel" data-slide-to = "1"></li>
					<li data-target = "#myCarousel" data-slide-to = "2"></li>
					<li data-target = "#myCarousel" data-slide-to = "3"></li>
				</ol>
				<!-- images에 저장된 사진들 불러오기 -->
				<div	class = "carousel-inner">
					<div class = "item active">
						<img src = "images/cat.jpg">
					</div>
					<div class = "item">
						<img src = "images/CatHunting.jpg">
					</div>
					<div class = "item">
						<img src = "images/huskyAnimal.jpg">
					</div>
					<div class = "item">
						<img src = "images/Redpanda.jpg">
					</div>
				</div>
				<!-- 사진들을 좌우로 넘기는 코드 -->
				<a class = "left carousel-control" href="#myCarousel" data-slide="prev">
					<span class="glyphicon glyphicon-cheron-left"></span>
				</a>
				<a class = "right carousel-control" href="#myCarousel" data-slide="next">
					<span class="glyphicon glyphicon-cheron-right"></span>
				</a>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script src="js/bootstrap.js"></script>
</body>
</html>