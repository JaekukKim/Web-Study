package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// 게시글들의 데이터베이스의 접근해서 데이터를 관리할 수 있도록 게시판 데이터 접근 객체를 만들어준다.
public class BbsDAO {
	private Connection conn;
	private ResultSet rs;

	// mysql에 접속을 할 수 있게 생성자를 초기화하는 코드를 만들어준다.
	public BbsDAO() {
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
	
	// 게시판 글쓰기를 위해선 3개의 메소드가 필요
	public String getDate() {
		// 현재시간 가져오는 메소드
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// 연결되어 있는 객체를 통해 SQL문자를 실행 준비 단계(초기화) 시켜준다.
			rs = pstmt.executeQuery(); // 실제로 실행된 결과를 나올수 있게
			if (rs.next()) { // executeQuery()의 읽어들일 값이 있으면 if문 돌아감.
				return rs.getString(1);
				// 결과가 있다면 현재의 날짜를 그대로 반환한다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // 데이터베이스 오류를 알려주는 빈 문자열
	}
	
	public int getNext() {
		// 게시글같은경우는 현재까지 쓰여진 글에서 1개씩 늘어나야 하기 때문에 마지막에 쓰여진 글을 가져와서 그 글의 번호에 1을 더해준다.
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// 연결되어 있는 객체를 통해 SQL문자를 실행 준비 단계(초기화) 시켜준다.
			rs = pstmt.executeQuery(); // 실제로 실행된 결과를 나올수 있게
			if (rs.next()) {
				return rs.getInt(1) + 1;
				// 결과가 있다면 마지막 게시글번호 + 1 해서 반환한다.
			}
			return 1; //첫번째 게시글 번호는 당연하게도 1이다.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류를 알려주는 -1, 게시글 번호는 음수가 될 수 없다.
	}
	
	public int write (String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// 연결되어 있는 객체를 통해 SQL문자를 실행 준비 단계(초기화) 시켜준다.
			
			// 위 SQL문의 인젝션을 방어하기 위한 ?에 들어갈 매개변수 6개ㅁ를 불러올수 있도록 만들어준다.
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1); // bbsAvailable같은 경우 게시글이 작성될 경우 당연하게 삭제가 안된 경우이므로 1을 넣어준다( 1== 삭제 안댐)
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류를 알려주는 -1 성공적으로 작성이 되었다면 음수가 나올 수 없다. (위에 보면 성공리턴값은 1~6이다)
	}
	
	// 게시글 글 목록 기능 구현하기, List 클래스를 사용
	// 게시글 목록에는 페이지라는 개념이 존재
	// 1페이지마다 10개씩 구현하도록 한다.
	public ArrayList<Bbs> getList (int pageNumber){
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		// BBS에 있는 bbsID로부터 Available이 1인 (삭제되지 않은) 글들을 bbsID 순으로 내림차순 (DESC)하여 10개씩 (LIMIT 10) 가져온다.
		// 중간 ? 는 SQL 인젝션 방어용이다.
		ArrayList<Bbs> list = new ArrayList<>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// 연결되어 있는 객체를 통해 SQL문자를 실행 준비 단계(초기화) 시켜준다.
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			// 이렇게 작성되는 이유는 getnext같은 경우는 다음으로 작성 될 글의 번호를 뜻한다. 글이 5개일때 getnext는 6일것이다.
			
			rs = pstmt.executeQuery(); // 실제로 실행된 결과를 나올수 있게
			while (rs.next()) {
				
				Bbs bbs = new Bbs();
				// Bbs를 객체화 시켜 객체에다가 정보들을 담아 낸다
				
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				
				list.add(bbs);
				// 담아낸 정보들을 list에 추가시켜준다. 익숙한부분.
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; 
	}
	
	// 페이지를 넘기는 메소드, 게시글이 10개가 넘어가면 다음페이지로 넘어가는 버튼 활성화.
	// 10단위로 딱 끊기면 페이지가 활성화 될 필요가 없다. 11,21 이런식부터 활성화된다.
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		// BBS에 있는 bbsID로부터 Available이 1인 (삭제되지 않은) 글들을 bbsID 순으로 내림차순 (DESC)하여 10개씩 (LIMIT 10) 가져온다.
		// 중간 ? 는 SQL 인젝션 방어용이다.
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// 연결되어 있는 객체를 통해 SQL문자를 실행 준비 단계(초기화) 시켜준다.
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			
			rs = pstmt.executeQuery(); // 실제로 실행된 결과를 나올수 있게
			
			if (rs.next()) {
				return true;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	// 게시글을 클릭했을때 내용을 보여주는 기능의 메소드를 만들어보자.
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		// BBS 테이블에서 bbsID를 가져온다. (게시글 작성자의 id)
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, bbsID);
			// 파라미터로 받은 bbsID의 첫번째를 가져온다.
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Bbs bbs = new Bbs();
				// Bbs를 객체화 시켜 객체에다가 정보들을 담아 낸다
				
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				// 게시글의 목록을 가져오듯이 게시글을 보여주는것도 똑같이 가져온다. 사용자의 정보들을 불러 와야만 게시글 확인이 가능하기 때문.
				
				return bbs;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 글 수정 메소드 구현.
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		// 글을 수정하는 메소드는 글을 작성하는 메소드와 연관성이 깊다. 데이터를 새로만들거나 뽑아오는게 아닌 데이터베이스 갚을 바꾸거나 추가하는 sql의 문장구조이다.
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		// bbsID에 해당하는 값을 찾아 그 값이 맞으면 제목과 내용을 불러와 수정할수 있게 (UPDATE) 만들어주는 sql문장이다.
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// 연결되어 있는 객체를 통해 SQL문자를 실행 준비 단계(초기화) 시켜준다.
			
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID); 
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류를 알려주는 -1 성공적으로 작성이 되었다면 음수가 나올 수 없다.
	}
	
	// 글 삭제 메소드
	public int delete (int bbsID) {
		
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
		// 맨 처음 만든 bbsAvailable은 글이 살아있는지 죽었는지 0과 1로 판별하는 변수였다. 그래서 쿼리문에 이제 bbsAvailable를 쓸 때가 온것이다.
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			// 연결되어 있는 객체를 통해 SQL문자를 실행 준비 단계(초기화) 시켜준다.
			
			pstmt.setInt(1, bbsID);
			// 첫번째 값으로 받아온 bbsID값의 글을 bbsAvailable을 0으로 만들어줘 게시글을 삭제처리 하는 것이다.
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터 베이스 오류. 반환이 성공하면 -1이 나올 수 없다.
		
	}
}
