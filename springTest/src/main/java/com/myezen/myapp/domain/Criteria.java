package com.myezen.myapp.domain;


public class Criteria {//게시판-페이징---" 
	//페이지 번호를 넘기는 기능을 하기 위한 클래스
	private int page;		//게시글 범위 시작 값 
	private int perPageNum; //게시글 범위 종료 값 화면에 출력되는 리스트 수
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 15;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) { 
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
}
