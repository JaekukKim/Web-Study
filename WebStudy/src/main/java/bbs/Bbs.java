package bbs;

// 게시글 정보를 담는 javabeans 생성. 전반적인 게시글 하나를 관리해주는 자바 파일이다.
public class Bbs {
	private int bbsID;
	private String bbsTitle;
	private String userID;
	private String bbsDate;
	private String bbsContent;
	
	private int bbsAvailable;
	// bbsAvailable은 현재 글이 삭제 되었는지 안되어있는지 알려주는 것이다. 데이터베이스 안에는 삭제된 글 정보까지 저장할 수 있도록 이런식으로 만들어 줘야한다.
	// 이게 1인경우 삭제 x , 0인경우 삭제 o 로 분간해서 활용해보자

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}

	public String getBbsTitle() {
		return bbsTitle;
	}

	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getBbsDate() {
		return bbsDate;
	}

	public void setBbsDate(String bbsDate) {
		this.bbsDate = bbsDate;
	}

	public String getBbsContent() {
		return bbsContent;
	}

	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
	}

	public int getBbsAvailable() {
		return bbsAvailable;
	}

	public void setBbsAvailable(int bbsAvailable) {
		this.bbsAvailable = bbsAvailable;
	}
}
