package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/ServletStudy/add")
public class AddExam extends HttpServlet{
	
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		// printwriter은 고정이나 마찬가지다. 웹 페이지에 출력할 수 있게 만드는거임.
		
		int x = Integer.parseInt(request.getParameter("x"));
		int y = Integer.parseInt(request.getParameter("y"));
		
		pw.println("x+y의 값은? : " + (x+y));
		// 위에서 만든걸 이용해서 출력.
		
		// 강사님 숙제후기 : 이게 뭐라고 어렵냐. 그래도 5분컷하긴함.
		
		// 강사님 풀이
//		String x_ = request.getParameter("x");
//		String y_ = request.getParameter("y");
//		
//		// 빈 문자열일 경우를 생각...! (사용자가 값을 아무것도 안넣었을때 0으로 전달)
//		int x = 0;
//		int y = 0;
//		
//		// 빈 문자열이 아닐 경우 값에 x y에 정수형으로 대입.
//		if (!x_.equals("")) {
//			x=Integer.parseInt(x_);
//		}
//		if (!y_.equals("")) {
//			y=Integer.parseInt(y_);
//		}
//		int result = x+y;
//		
//		pw.printf("result is %d\n", result);
	}
}
