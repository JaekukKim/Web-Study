package com.study.web.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ServletStudy/calculator")
public class Calculator extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// get 요청인지 확인.
		if (request.getMethod().equals("GET")) {
			// request의 메소드 중에는 메소드를 얻어내는 getmethod()라는 메소드가 존재한다.
			// 그럼 html파일 경로 안에 있는 메소드를 문자열로 반환해준다.
			// 여기서 문자열로 반환해주니 문자열을 확인하는 equals메소드를 사용해 주어야 하는데. 매우 중요한 점 중 하나는
			// html 내에선 method = "get" 라고 요청을 줘도, 문자열을 비교하는 과정은 반드시 대문자로 해 주어야 한다는 점이다.
			// 위의 메소드는 구분자를 대문자를 사용한다는 얘기이다.
			System.out.println("get요청이 들어왔습니다.");

			// post 메소드인지 확인.
		} else if (request.getMethod().equals("POST")) {
			System.out.println("post요청이 들어왔습니다.");
		}
		/*
		 * 위와 같은 방법으로 요청방식 메소드를 확인할 수 있지만. 우리는 부모 생성자의 service메소드를 이용해 확인 할 수 있는 획기적인
		 * 방법을 가지고 있다. 아래를 보자.
		 */

		super.service(request, response);
		/*
		 * 위 부모생성자가 하는 역할이 무엇이냐...? 부모가 갖고있는 서비스 함수는 doGet() doPoset()라는 메소드가 있다. 만약
		 * get요청이면 doget라는 메소드 호출, post면 dopost라는 메소드가 호출이 된다. 만약 부모생성자가 있는 상태에서
		 * 메소드가 "오버라이딩"이 되지 않았다면 오류가 나므로 반드시 (get인지 post인지) 오버라이딩을 해 주어야 한다.
		 * 오버라이딩을 해주지 않았다면 405 (url은 있으나 메소드를 처리할 수 없는 로직이 없다.) 오류가 발생한다.
		 */

		// 이제 오버라이딩을 해 주어 doGet, doPost를 구현해보자

	}
	/*
	 * 이제 위의 if문으로 구현했던 get메소드와 post메소드 확인 여부를 아래 오버라이딩을 통해 만들어볼려고 한다.
	 * get요청이면 doGet출력, post면 doPost출력이 되게 만들어볼려한다.
	*/
	
	// 오버라이딩은 반드시 service() 메소드 밖에다 호출해주어야 한다 잉 왜 안돼징;; 하지 말것..... (본인이 그랬음... ㅎㅎ;)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet 메소드가 호출되었습니다.");
		resp.sendRedirect("calculator.html");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost 메소드가 호출되었습니다.");
		resp.sendRedirect("calculator.html");
		
	}
	// 만약 오버라이드를 하지 않으면 서비스 함수는 요청에 맞는 메소드를 불러와 처리를 한다.
	
	// 아주 만족스럽다 결과는
//	get요청이 들어왔습니다.
//	doGet 메소드가 호출되었습니다.
//	post요청이 들어왔습니다.
//	doPost 메소드가 호출되었습니다.
	// 이렇게 들어온다.

}
