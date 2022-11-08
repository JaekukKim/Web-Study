package com.study.web.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class ServletCharacterFilter implements Filter {
// 서블릿 필터를 생성하는 코드들이다.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		// 모든 요청에 대해 요청이 들어오면 위 문구가 실행이 된다. 처음에 톰캣이 실행이 될 때 한번 출력된다.
		// 위 내용은 모든 요청(입력)에 대해 UTF-8방식으로 문자열을 인코딩 하겠다는 의미이다. 만약 위 문구가 없다면
		// 분명 한글은 잘 입력이 되는데 내 눈에만 한글로 보이지 웹은 한글로 인식을 못한다.
		
		// 매우 중요한 디자인패턴의 메소드이다. 이 필터의 전과 후로 필터링이 나뉜다. 필터링이 이미 들어간 뒤에는 절대로 다시 되돌릴 수 없다.
		chain.doFilter(request, response);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset = UTF-8");
		// 필터에서 거치고 난 다음에는 보여지는 방식을 출력해야한다. 서버에서의 응답은 response 이므로 캐릭터인코딩 메소드와
		// 내용 타입 메소드를 불러 어떻게 보여질지를 선언해야한다. 그리고 이 메소드들은 doFilter 메소드 뒤에 나와야 한다. (출력이기때문)
		

	}

}
