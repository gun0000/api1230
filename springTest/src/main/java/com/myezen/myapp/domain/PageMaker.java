package com.myezen.myapp.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

@Component //객체생성 컨포넌트로 정의  (객체로만들어진다) 서버가 실행할때
public class PageMaker {//"---2023-03-21----jsp 게시판-페이징---" 
	//페이지 네비게이션을 사용하기 위한 기능을 담긴 클래스
	
	private int displayPageNum =10; //페이지네비게이션 나타나는 페이징수 ex) 1 2 3 4 5 6 7 8 9 10
	private int startPage; //페이징의 시작점
	private int endPage; //페이징의 끝점
	private int totalCount; //전체 게시물 수  BoardDao에서 boardTotal로 꺼내온게 들어감
	
	private boolean prev; //이전버튼
	private boolean next; //다음버튼
	
	private SearchCriteria scri; //게시글 범위 시작 값 //게시글 범위 종료 값

	public SearchCriteria getScri() {
		return scri;
	}

	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcDate(); // 페이징 계산식
	}
	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}


	
	private void calcDate() {
		//끝페이지 번호
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum);
		
		//시작페이지 번호
		startPage = (endPage - displayPageNum)+1;
		
		//실제페이지
		int tempEndPage = (int)(Math.ceil(totalCount / (double)scri.getPerPageNum()));
		
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		//이전 다음 버튼 보여주기
		prev =(startPage == 1 ? false : true); //삼항연산자  false면 이전 버튼 안나타내기
		next =(endPage*scri.getPerPageNum() >= totalCount ? false : true);
		
		
	}
	
	public String encoding(String keyword) { //"---2023-03-21----jsp 게시판-검색하기---"
		String str = "";
		
		try {
			str=URLEncoder.encode(keyword,"UTF-8"); //키워드를 받아서 utf-8 번환하고 반환한다
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		
		return str;
	}
	
	
}
