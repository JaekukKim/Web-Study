package com.study.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/ServletStudy/keepstate")
public class KeepStateExam extends HttpServlet {

	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		// 값을 저장하는 도구가 무엇이냐? 바로 아래 문장인 request.getServletContext() 이다.
		// 서블릿 컨텍스트. 어플리케이션 저장소 라고 생각하면된다. 아래 문장과 같이 객체를 만들어 써줘야한다.
		ServletContext application = request.getServletContext();

		PrintWriter pw = response.getWriter();

		// 사용자 요청의 값을 저장할수있냐, 그리고 그 값을 꺼낼수 있냐, 를 이제 알아보자.
		// 값은 KeepStateExam.html에 있듯이 하나만 받는다.
		String value_ = request.getParameter("value"); // <= 문자열은 클라이언트의 html페이지에 들어온 값을 말한다.
		String op = request.getParameter("operator");

		// 기본값을 반드시 설정해주어야 한다. 오류페이지 예방. 즉, 사용자가 웹 페이지에서 값을 입력하지 않는 경우이다.
		int value = 0;

		if (!value_.equals("")) {
			value = Integer.parseInt(value_);
		}

		// operator가 무엇인지를 판별해주어야 한다. html파일에 값이 value가 어떤지를 보자.
		// operator는 + - = 3가지가 있지만 + - 는 값을 "저장"하는 역할을 한다. 연산을 하는 역할을 가진 값은 = 밖에 없으므로
		// = 만 어떻게 처리할지 설정해 주고, 값이 저장됬을때는(=가 아닐때는) else문으로 넘어가게 만든다.
		if (op.equals("=")) {

			// 여기 if문은 값을 "계산"

			// 이제 값을 불러와야한다.

			// x라고 하는건 앞에서 저장을 했던 값이다. 어플리케이션 저장소에 담겨져 있는 내용을 꺼내서 x라고 한다.
			int x = (Integer) application.getAttribute("value");
			/*
			 * 위의 x값을 저장한 부분이 핵심이라 생각한다. 지금 공부하는 부분이 ServletContext, 즉 application 저장소인데. 위의
			 * 코드는 어플리케이션 저장소에서 getAttribute를 이용해 저장된 "값" 즉 선대 서블릿이 setAttribute해놓고 떠나간 값을
			 * 가져오는과정의 코드인 것이다. 연산이 제대로 실행이 된다면 우리는 "값의 상태유지"에 성공한것이다.
			 */

			// y는 "지금 사용자가 전달한 value값"이 y이다.
			/*
			 * 이 부분이 굉장히 ambiguous하다 (요즘 sql배워서 한번써봄 ㅎㅎ;) 지금 사용자가 전달한 값이라는건. getAttribute를
			 * 거치지 않고 들어왔다는 얘기이다. 음. 처음에 들어온 값은 x에 저장되니 저장된 값을 "빼서"쓰는거고 y값은 저장 할 필요 없이 바로 쓰면
			 * 되니까 String value_ = request.getParameter("value") => if (!value_.equals(""))
			 * { value = Integer.parseInt(value_); } 를 거치고 난 뒤의 value를 바로쓰면 되는구나!!
			 */
			int y = value;

			String operator = (String) application.getAttribute("op");
			// getAttribute를 하기 위해선 반드시 setAttribute로 값을 "세팅" 해 주어야한다 만일 그렇지 않다면 null값을
			// 반환한다.
			int result = 0;

			if (operator.equals("+")) {
				result = x + y;
			} else if (operator.equals("-")) {
				result = x - y;
			} else if (operator.equals("x")) {
				result = x * y;
			}
			pw.println("연산 결과는?: " + (result));

		} else {
			// 여기 else문은 값을 "저장", 무언가를 연산하기 위한 공간이 아니다! 진짜로 "저장"만 하는 것이다.

			// 위 서블릿 컨텍스트 application에 값 2개를 저장 할 것이다. 이 application은 컬렉션이라 생각하면 된다.
			// setAttribute() : 키와 값을 넣게 되는 map 컬렉션이라고 생각하면 된다.
			/*
			 * "value"라고 하는 키 값을 받아 String value_ = request.getParameter("value") -> value =
			 * Integer.parseInt(value_)의 과정을 거친 value를 저장한다.
			 */
			application.setAttribute("value", value);
			application.setAttribute("op", op);

		}

	}
}
