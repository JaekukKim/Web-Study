package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forStates")
public class ServletForState extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		// setcontenttype는 어떤 방식으로 해석해야 할지를 정해주는 메소드이다.
		// 위 문구는 문자열을 utf-8로 해석을 하고, 이 문서는 html문서라고 얘기해주는 내용이다.
		// 그럼 크롬이든, 익스플로러든, 엣지든 어느 브라우저에서 키든간에 UTF-8, html로 인식한다는 의미이다.
		// 국내에 있는 한 setContentType 메소드 내에 있는 매개변수 형식은 외우도록 하자.
		
		for (int i = 0; i < 50; i++) {
			out.println((i+1) + " 안녕 서블릿!<br/>");
		}
	}

}
