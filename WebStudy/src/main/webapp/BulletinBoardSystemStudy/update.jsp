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
<title>JSP 게시판 글 수정 페이지.</title>
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
	
	// 유저가 로그인이 되지 않았을 시 출력할 결과.
	if(userID == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인이 필요합니다.')");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
	}
	
	// 게시글의 번호를 받아내 정수타입으로 변환 후 문자열이였던 bbsID를 정수타입으로 바꿔준다. (DAO에 있는 메소드를 이용하기 위함.)
	// 왜? 웹에서 우리 눈엔 숫자로 보일지 몰라도 받아내는 타입은 전부 모조리 "문자열"이기 때문이다. 그래서 서버에서 처리를 잘 해주어야 한다.
	int bbsID = 0;
	if (request.getParameter("bbsID") != null) {
		bbsID = Integer.parseInt(request.getParameter("bbsID"));
	}

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
	if(!userID.equals(bbs.getUserID())){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
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
			<!-- 글을 수정하기 위해선 당연히 로그인이 되어 있는 상태기 때문에 이 페이지에선 로그인이 안됬을시의 기능이 필요가 없다. -->
			
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
		</div>
	</nav>
	<div class="container">
		<!-- 특정한 내용을 담을 컨테이너를 지정한다. -->

		<div class="row">
			<form method="post" action="updateAction.jsp?bbsID=<%= bbsID %>">
			<!-- post방식으로 updateAction.jsp파일로 위에서 받아온 bbsID를 쿼리스트링의 매개변수로 전달한다. -->
			
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">


					<thead>
						<!-- 테이블의 제목 부분이라 할수있다. 각각의 속성들을 알려준다. -->

						<tr>
							<!-- 테이블의 "행"을 나타내는 tr태그이다. 즉 한 줄. -->

							<th colspan="2"
								style="background-color: #eeeeee; text-align: center;">게시판
								글수정</th>
							<!-- colspan="n"은 n개만큼의 열을 잡아먹을수 있도록 설정해주는 요소이다. -->
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control"
								placeholder="글 제목" name="bbsTitle" maxlength="50" value="<%=bbs.getBbsTitle()%>"></td>
								<!-- 글 제목에는 수정하기 전 글 제목을 표시해주면 편의성이 극대화된다. 아래의 글 내용도 마찬가지. -->
						</tr>
						
						<tr>
							<td><textarea class="form-control" placeholder="글 내용"
									name="bbsContent" maxlength="4096" style="height: 350px;"><%=bbs.getBbsContent()%></textarea></td>
						</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="글수정">

			</form>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>