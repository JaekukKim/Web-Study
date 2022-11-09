package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/ServletStudy/calculate")
public class CalcExam extends HttpServlet{
	
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String x1 = request.getParameter("x");
		String y1 = request.getParameter("y");
		String op = request.getParameter("operator");

		
		int x = 0;
		int y = 0;
		double result = x+y;
		
		if (!x1.equals("")) {
			x = Integer.parseInt(x1);
		}
		if (!y1.equals("")) {
			y = Integer.parseInt(y1);
		}
	
		switch (op) {
		case "덧셈":
			result = x+y;
		break;
		case "뺄셈":
			result = x-y;
			break;
		case "나눗셈":
			result = x/y;
			break;
		case "곱셈":
			result = (int)x*y;
			break;
		}
		// 지난 과제에 submit 버튼 여러개 만들기를 더하여 switch문으로 웹에서의 사칙연산 출력을 만들어 보았다.
		// 근데 왜 나눗셈이 소숫점이 출력이 안돼지...?

		pw.println("x+y의 값은? : " + (result));
		
	}
}
