package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/ServletStudy/array")
public class AddExamArray extends HttpServlet {

	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		
		String[] num1 = request.getParameterValues("num");
		// 값을 여러개를 받는다면, 즉 배열"같이" 받는다면 여러개를 받아야 하기 때문에 getParameterValues
		// 메소드를 사용해야 한다.

		int result = 0;
		
		// 값을 "배열의 형태"로 받기 때문에 for문으로 값을 하나하나 출력해 주어야 한다.
		for (int i = 0; i < num1.length; i++) {
			
			// 빈 값이 들어간다면 웹에서 500 에러가 나기때문에
			// "값이 입력되지 않았을 때"를 반드시 처리해 주어야 한다. SQL에서 null값 처리 하는거랑 비슷한 개념이라 생각하니 편하다.
			if (num1[i].equals("")) {
				num1[i] = "0";
				
			} else {
				int num2 = Integer.parseInt(num1[i]);
				result += num2;
			}
		}


		pw.printf("result is %d\n", result);
		// 이렇게 되면 웹 페이지에선 num=1&num=2&num=3&num=4 이런식으로 전달된다.
		// 만약 get방식으로 전달했다면 url에 쿼리스트링으로 위의 내용이 뜰 것이다.
	}
}
