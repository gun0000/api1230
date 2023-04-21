package com.myezen.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myezen.myapp.domain.CommentVo;
import com.myezen.myapp.persistance.CommentService_Mapper;

@Service("commentServiceImpl")
public class CommentServiceImpl implements CommentService {
	
private CommentService_Mapper csm;//전역으로 쓰고 싶어서 전역변수 선언
	
	//생성자 db연동하고 맵퍼 연결 Mybatis활용
	@Autowired
	public CommentServiceImpl(SqlSession sqlSession) {//이거는 서버가 실행할때 같이 켜짐
		this.csm = sqlSession.getMapper(CommentService_Mapper.class);//getMapper(type)을받는다 인터페이스 Mybatis용 따로 인터페이스를 만든다
	}
	
	
	@Override
	public int commentInsert(CommentVo cv) {
		
		int value = csm.commentInsert(cv);
		
		return value;
	}


	@Override
	public ArrayList<CommentVo> commentSelectAll(int bidx,int nextBlock) {
		ArrayList<CommentVo> alist = csm.commentSelectAll(bidx,nextBlock);
		return alist;
	}

	@Override
	public int commentTotalCnt(int bidx) {
		int value = csm.commentTotalCnt(bidx);
		return value;
	}


	@Override
	public int commentOneDelete(int cidx) {
		int value = csm.commentOneDelete(cidx);
		return value;
	}

}
