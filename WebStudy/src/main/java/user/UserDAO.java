package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 로그인 기능을 구현하기 위한 jsp에서 회원 db테이블에 접근하기 위해 DAO를 만들어야한다. DAO = database 접근 object.
public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	// 필드를 선언하는데 이 필드들은 sql문을 사용하기 위한 필드이다.

	// mysql에 접속을 할 수 있게 생성자를 초기화하는 코드를 만들어준다. db에 접근하기 위한 url,id,비밀번호를 입력해야하는 과정을 코딩한다.
	public UserDAO() {
		// try catch로 예외처리를 해준다 뭐때문에 틀렸는지는 알아야한다.
		try {
			String dbURL = "jdbc:mysql://localhost:3306/WebStudy?serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "mysql1103~~";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			// conn 객체 안에 접속된 정보가 담김.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 로그인 시도 메소드 작성.
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			// 어떠한 정해진 sql문자를 데이터베이스에 삽입하는 형식으로 인스턴스를 가져온다.
			pstmt.setString(1, userID);
			// sql 인젝션이란 해킹 기법을 방어하기 위한 수단으로써 prepareStatement란걸 이용.
			// 하나의 문장을 미리 준비해 놓았다가 중간에 ? 같은걸 넣어놔 나중에 ? 에 해당하는 내용으로 유저 id를 넣어준다.
			// 매개변수로 넘어온 유저 id를 물음표에 들어갈수 있게 해줌으로써 실제로 데이터베이스에는
			// 현재 접속을 시도하고자 하는 사용자의 id를 입력받아 그 아이디가 실제로 존재 하는지 존재 한다면
			// 비밀번호는 무엇인지 데이터베이스에서 가져오도록 하는 것이다.
			rs = pstmt.executeQuery();
			if (rs.next()) {

				if (rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공 리턴값.
					
				} else {
					return 0; // 비밀번호 불일치의 리턴값.
				}

			}
			return -1; // 아이디가 없다는 리턴값.

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // -2는 데이터베이스 오류를 의미한다.
	}
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			
			/// 위 SQL문법에 해당하는 ? 5개의 값에 채워넣기 위해 prepareStatement 메소드를 사용하여 먼저 세팅해준다.
			// 그리고 SQL테이블에 있는 순서를 꼭꼭 지켜가면서 입력받은 데이터를 저장할 수 있게 set해준다.
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
			
			// 인서트 문장을 실행한 경우는 반드시 0 이상의 숫자가 반환되기 때문에 -1이 아닌 경우는 성공적으로 회원가입이 이루어진다.
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터 베이스 오류가 났을때.
	}
}
