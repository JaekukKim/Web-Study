package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/defaultStr")
public class defalutQueryString extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		PrintWriter out = resp.getWriter();
		
		// [1]
		String clientReq2 = req.getParameter("clientReq");
		// 이렇게 입력받은 파라미터를 저장할 임시변수를 선언 해 준다. 이것은 "임시변수"이므로 아래 변수랑 절대 같지않다!
		
		// [2]
		int clientReq = 20;
		// 그리고 입력받은 파라미터의 기본값을 반드시 설정해주어야 한다!

		// [3]
		if (clientReq2 != null && !clientReq2.equals("")) {
			//[4]
			// 그리고 입력받은 파라미터의 값이 어떤것인지 판단해주는 코드를 작성해야한다.
			// null값이 아님과 동시에 ""(빈공백)이 아니라면 아래의 코드를 수행한다.
			// 실수한부분 : !clientReq2.equals("") 이부분에서 논리연산자 !를 빼먹었었다. 이런경우엔 null값이 아님과 동시에 빈공백이여야 실행이 되는건데
			// 애초에 이 조건은 말이 안돼는 조건이다. ㅎㅎ;;
			
			//[5]
			clientReq = Integer.parseInt(clientReq2);
		}
		// 여기도 [5]
		for (int i = 0; i < clientReq; i++) {
			out.println((i + 1) + " 안녕 서블릿!<br/>");
		}
		
		// 쿼리스트링에 전달된 파라미터는 "무조건 문자열!!!" [(ex) ?clientReq=3 <= 이부분에서 3은 정수타입 3이 아니라 "3"이다!!!]
		// [1] 요청받은 쿼리스트링의 파라미터를 저장 할 임시변수를 선언. (clientReq2)
		// [2] 쿼리스트링 파라미터의 기본값을 설정한다. (clientReq)
		// [3] 그리고 쿼리스트링 파라미터가 저장된 "임시변수"를 판별하는 조건문 선언 (if문)
		// [4] 조건문 내에서 임시변수 (clientReq2의 값) 를 판별하고.
		// [5] 조건에 맞게 출력한다.
		// 기본값 설정 완성!! 이제 쿼리스트링의 파라미터를 "반드시" 제공 해야 할 필요가 없어졌다!!

	}

}
