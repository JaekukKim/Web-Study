package com.study.web.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class JdbcStudy {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		String url = "jdbc:mysql://localhost:3306/study1?serverTimezone=UTC";
		String sql = "SELECT * FROM NOTICE";

		Class.forName("com.mysql.cj.jdbc.Driver");
		// 드라이버 매니저를 실행한다.
		Connection con = DriverManager.getConnection(url, "study", "1234");
		// 드라이버 매니저에 sql 아이디 비밀번호를 이용하여 연결객체를 만들어준다.
		Statement st = con.createStatement();
		// 연결 객체를 이용해 생성된 실행객체를 만들어주고.
		ResultSet rs = st.executeQuery(sql);
		// 이제 데이터를 담을 그릇객체를 만들어준다.

//		rs.next();
//		String title = rs.getString("title"); // 컬럼명을 가져오는데 컬럼명은 대소문자를 구분하지 않는다.
//		System.out.println(title);
		// 이 상태로 가져오면 end of file에 있다는 sql예외가 뜬다. 이걸 해결하기 위해선 if문으로 감싸야한다.

		while (rs.next()) { // while 문으로 모든 데이터를 뽑아낼 수 있다.

			int id = rs.getInt("ID");
			String title = rs.getString("title");
			String writerID = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regDate = rs.getDate("REGDATE");
			String files = rs.getString("FILES");
			int hit = rs.getInt("HIT");

			System.out.printf("id : %d, title : %s, writerID : %s, content : %s, regDate : %s, hit : %d, files : %s\n",
					id, title, writerID, content, regDate, hit, files);

		} // [1] 이제 저장되어 있는 테이블중에 title에 저장되어 있는 jdbc실습이 출력이된다!!
			// [2] 저장되어 있는 데이터들을 순차적으로 전부 출력한다.
		
		// 조회수가 10 이상인 게시글만 출력하세요 라는 문제가 주어졌는데 어떻게 해야할까?
		// 보통 저기 위에 if(hit>10)이렇게 하겠지만..... 답은 완전 다른곳에 명시되어 있었다.
		// 바로 sql문을 SELECT * FROM NOTICE WHERE HIT >10 으로 바꾸는 것이였다.. 충격;

		rs.close();
		st.close();
		con.close();
		// 프로그램이 종료되고 나서 데이터가 반환되긴 하지만 사용이 끝나자마자 반환 해 주는게 아무래도 좋다.
	}
}
