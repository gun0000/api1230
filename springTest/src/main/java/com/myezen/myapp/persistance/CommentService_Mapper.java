package com.myezen.myapp.persistance;

import java.util.ArrayList;

import com.myezen.myapp.domain.CommentVo;

public interface CommentService_Mapper {
	
	//댓글달기
	public int commentInsert(CommentVo cv);
	//댓글리스트뽑기
	public ArrayList<CommentVo> commentSelectAll(int bidx,int nextBlock);
	//댓글총갯수뽑기
	public int commentTotalCnt(int bidx );
	//댓글삭제
	public int commentOneDelete(int cidx);
}
