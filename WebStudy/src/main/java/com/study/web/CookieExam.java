package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ServletStudy/CookieExam")
public class CookieExam extends HttpServlet {

	// 이번엔 Cookie다 공부하자.
	// 지난번 세션페이지 재활용이당.
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		// 쿠키는 배열로 선언이된다! 그리고 메소드와는 달리 쿠키는 cookie이다. s가 빠졌다... 이런부분은 햇갈리니 참고하여 외워두자 그냥

		PrintWriter pw = response.getWriter();

		String value_ = request.getParameter("value"); // <= 문자열은 클라이언트의 html페이지에 들어온 값을 말한다.
		String op = request.getParameter("operator");

		int value = 0;

		if (!value_.equals("")) {
			value = Integer.parseInt(value_);
		}

		if (op.equals("=")) {
			// 이 부분에서 저장된 쿠키가 읽혀져야 한다
			// (앞전 app,session 부분 코드에서 =을 눌렀을때 저장된 값이 읽혀지는 형식으로 코딩이 되었다.)
			// 배열 형식의 쿠키이니 반복문이나 인덱스넘버로 꺼내야한다.
			int x = 0;
			for (Cookie c : cookies) {

				// 일단 가져온 쿠키의 "이름"이 클라이언트가 입력한 "value"와 같은지를 본다.
				if (c.getName().equals("value")) {

					// 만약 같다면 x에 정수타입으로 대입한다. (다시한번 강조. 쿠키는 문자열만가능하다!!)
					x = Integer.parseInt(c.getValue());

					// 그리고 찾았다면 반복문을 멈춰준다.
					break;
				}
			}

			int y = value;

			String operator = "";
			// 위와 마찬가지로 operator도 반복문으로 찾아내야한다.
			for (Cookie c : cookies) {

				if (c.getName().equals("op")) {

					// 만약 같다면 operator에 대입해준다.
					operator = c.getValue();

					// 그리고 찾았다면 반복문을 멈춰준다.
					break;
				}
			}

			int result = 0;

			if (operator.equals("+")) {
				result = x + y;
			} else if (operator.equals("-")) {
				result = x - y;
			} else if (operator.equals("x")) {
				result = x * y;
			}
			pw.println("연산결과는? : " + (result));

		} else {
			// 사용자에게 전달받은 키와 값을 쿠키로 심어주자.
			Cookie valueCookie = new Cookie("value", String.valueOf(value));
			// 쿠키 값으로 보낼 수 있는 url에 사용할수있는 형태의 "문자열"로만 보내야한다!

			Cookie opCookie = new Cookie("op", op);

			// 이제 쿠키로 만들어준 값을 사용자에게 보내주어야한다. 만들기만 하면 절대안됀다 당연함.
			// 이렇게 되면 response의 Header에 심어진 형태로 전달이 된다.
			response.addCookie(valueCookie);
			response.addCookie(opCookie);

		}

	}
}
