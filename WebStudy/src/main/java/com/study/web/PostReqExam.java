package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 이 문서는 PostRequest.html과 연관되어진다.
@WebServlet("/notice-reg")
public class PostReqExam extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String title = req.getParameter("title");
		// div 태그에 있는 title을 입력받아 String title에 저장하고 요청처리를 하고 다시 돌려준다.
		String content = req.getParameter("content");
		// textarea 태그에 있는 name을 입력받아 String content에 저장하고 요청처리를 하고 다시 돌려준다.
		
		// post 요청을 받은 값을 다시 돌려주는 과정이다.
		out.println(title);
		out.println(content);

	}

}
