package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;

public interface BoardService {

	
	//게시글 리스트 불러오기
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	//게시글 총갯수
	public int boardTotal(SearchCriteria scri);
	//게시글 조회수
	public int boardViewCnt(int bidx);
	//게시글 하나보기
	public BoardVo boardSelectOne(int bidx);
	//게시글 쓰기
	public int boardInsert(BoardVo bv);
	//게시글 수정
	public int boardModify(BoardVo bv);
	//게시글 삭제
	public int boardDelete(BoardVo bv);	
	//게시글 답변
	public int boardReply(BoardVo bv);//쿼리가 두개가 동작	
	
	
	
	
	
}
