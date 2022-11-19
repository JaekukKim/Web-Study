package com.study.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 어노테이션 맵핑은 겹치면 안된다. 반드시 바꿔줘야할것 ㅎㅎ.
@WebServlet("/QueryStr")
public class GetAndQueryStringExam extends HttpServlet {
	// 지난번 forstate 예제를 가져와서 이어서 할 예정이당.
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		PrintWriter out = resp.getWriter();

		// 쿼리스트링의 요청사항을 읽기 위한 코드를 작성해 주었다. 서버는 clientReq라고 하는 값을 읽는데
		// 이 조건은 "무조건"문자열로 전달되기 때문에 정수형으론 쓸 수 없다. 그래서 이걸 정수형으로 변환(Integer.parseInt()) 해
		// 주어야 한다.
		// 클라이언트가 전달 해 주었을때 이 전달해준 수 만큼 반복이 된다.
		int clientReq = Integer.parseInt(req.getParameter("clientReq"));

		// 그리고 요청받은 수 만큼 반복하게 만들어준다. 그럴라면? i값은 clientReq보다 작은만큼 반복되야한다.
		for (int i = 0; i < clientReq; i++) {
			out.println((i + 1) + " 안녕 서블릿!<br/>");
		}

		// 실행 결과 500-내부서버오류가 난다 뭐지? 자바에서의 예외는
		// 심각: 경로가 []인 컨텍스트의 서블릿 [com.study.web.GetAndQueryStringExam]을(를) 위한
		// Servlet.service() 호출이 예외를 발생시켰습니다.
		// java.lang.NumberFormatException: Cannot parse null string
		// 이렇게 뜬다. 이 문제는 req.getParameter("clientReq") 이부분인데 지금 이 상태의 코드는 반드시
		// clientReq라고 하는 쿼리값을 받아야 하는 상황이라예외 및 오류가 발생한 것이였다. 이럴땐 url뒤에 쿼리값을 지정해주면 된다.
		// http://localhost:8080/QueryStr?clientReq=5 이런식으로 말이다. 쿼리값을 씌우면 오류가 나지 않는다.
	}

}
