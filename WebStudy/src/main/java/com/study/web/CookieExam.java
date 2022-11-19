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
			int x = 0;

			// 이 부분에서 저장된 쿠키가 읽혀져야 한다
			// (앞전 app,session 부분 코드에서 =을 눌렀을때 저장된 값이 읽혀지는 형식으로 코딩이 되었다.)
			// 배열 형식의 쿠키이니 반복문이나 인덱스넘버로 꺼내야한다.
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

			valueCookie.setPath("/ServletStudy/CookieExam");
			opCookie.setPath("/ServletStudy/CookieExam");
			/*
			 * [2022-11-17 쿠키 path 설정]
			 * "쿠키에 경로를 설정해주는" setPath메소드이다.
			 * 이 메소드 안에 경로를 설정해주면 그 경로에 해당되는 곳에만 쿠키를 전달하게 된다.
			 * [1] /(루트) 로 설정해주면 모든 페이지를 요청할때 마다 쿠키를 전달한다는 얘기이다.
			 * [2] 위처럼 특정 url이 들어가게 된다면 특정 url에만 쿠키를 전달한다.
			 * [3] 만약 /notice/ 이런식으로 한다면 notice가 들어간 url경로를 요청할 때 마다 쿠키를 전달한다는 얘기이다.
			 * 위의 3가지 방식은 기억해두자.
			 * 참고 : 만약 위의 path의 경로를 특정url로 지정할라면 경로를 모두 일일히 설정해주어야 한다.
			 */

			
			 /*	
			  * [2022-11-17 쿠키 maxAge설정]
			 	쿠키의 수명을 설정해주자. maxAge()라는 메소드를 사용한다.
			 	
			 	valueCookie.setMaxAge(1000);
			 	
			 	1000초 후에 쿠키가 지워진다는 얘기이다.
			 	쓰레드와는 달리 괄호 안에 들어간 숫자는 초단위로 설정이 된다. 하지만 대충 초를 집어넣는다면 감이 안잡힐것이다.
			 	그래서 우리는 위의 방식보다는 아래의 방식대로 많이 쓴다.
			 */
			valueCookie.setMaxAge(24 * 60 * 60);
			/*
			 * 확실히 직관적이지 않은가? 60초 x 60초 = 3600초 = 1시간! 이라고 우리는 보자마자 알 수 있게 되는것이다.
			 * 만약 위 메소드를 하루로 설정하고싶다면?? 24를 곱해주면 된다. 1시간x 24 = 24시간 = 1일. 
			 * 여기서 뭔가 번뜩이는 사람들이 있을텐데 "어.. 하루동안 보지않기...?" 라는 생각이 든 사람이 분명 있을것이다. (저도 ㅎㅎ)
			 * 어느 사이트를 들어가면 광고 팝업이 뜨면서 하루동안 보지않기 라는 체크박스가 왼쪽 아래에 있는걸 본적이 있을것이다.
			 * 맞다. 그때 쓰이는 그 체크박스가 수명이 정해진 쿠키를 서버에서 클라이언트한테 주는것이다!
			 * 수명이 다 한 쿠키는?? 당연히 없어진다.
			 * 쿠키의 수명은 쿠키가 "생성된 시점"부터 시작된다. 즉 서버가 클라이언트에게 보내준 시점부터이다.
			 */

			// 이제 쿠키로 만들어준 값을 사용자에게 보내주어야한다. 만들기만 하면 절대안됀다 당연함.
			// 이렇게 되면 response의 Header에 심어진 형태로 전달이 된다.
			response.addCookie(valueCookie);
			response.addCookie(opCookie);
			
			/*
			 * 2022 - 11 - 19 쿠키를 삭제하는 과정이다. 
			 * 만약 웹 페이지에서 reset 버튼을 클릭한다면 쿠키의 수명을 강제로 0으로 만드는 식으로 하여
			 * 쿠키를 지워버리는 작업이다.
			*/
			
			if (op != null && op.equals("reset")) {
				valueCookie.setMaxAge(0);
				opCookie.setMaxAge(0);
			}

			/*
			 * [2022-11-17 사용자에게 페이지를 돌려주는 redirect
			 * 현재 이 코드는 연산은 성공적으로 가능하나 연산기호를 눌러서 입력값을 전달 할 시 백지상태로 넘어가는 치명적인 단점이 존재했다.
			 * 이제 그걸 해결해보려고 한다. 아주아주 간단하다 sendRedirect() 메소드 하나로 ok
			 */
			
			response.sendRedirect("CookieExam.html");
			// 이 얼마나 멋진가 그냥 안에다가 돌려보낼 html파일 이름만 넣어주면 된다. 여기에 다른 페이지로 돌려보내고 싶다? 얼마든지 가능하다.
			// 위 메소드가 실행되었다는 증거는 웹 페이지에서 한번 값을 요청하면 "뒤로가기"버튼이 활성화 되었다는 것이다.
			// 이로써 할 수 있는건 저 위의 CookieExam.html "다시 요청" 했다는 것이다.
			
			
		}

	}
}
