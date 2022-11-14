package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ServletStudy/SessionExam")
public class SessionExam extends HttpServlet {

	// Session 객체에 대해 공부하자공부하자
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpServletRequest와 ServletRequest 차이점 깃헙 댓글에 꼭 쓰기.

		ServletContext application = request.getServletContext();

		// 세션을 객체화 시켜준다.
		HttpSession session = request.getSession();

		PrintWriter pw = response.getWriter();

		String value_ = request.getParameter("value"); // <= 문자열은 클라이언트의 html페이지에 들어온 값을 말한다.
		String op = request.getParameter("operator");

		int value = 0;

		if (!value_.equals("")) {
			value = Integer.parseInt(value_);
		}

		if (op.equals("=")) {

			// int x = (Integer) application.getAttribute("value");
			int x = (Integer) session.getAttribute("value");
			// 위의 application을 session으로 바꿔줬는데 전혀 위화감이 없다. 그렇다 사용방법은 거의 똑같다.
			int y = value;

			// String operator = (String) application.getAttribute("op");
			String operator = (String) session.getAttribute("op");

			int result = 0;

			if (operator.equals("+")) {
				result = x + y;
			} else if (operator.equals("-")) {
				result = x - y;
			}
			pw.println("연산결과는? : " + (result));

		} else {

			session.setAttribute("value", value);
			session.setAttribute("op", op);

		}

	}
}
