package com.myezen.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.persistance.BoardService_Mapper;

@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService {

	private BoardService_Mapper bsm;//전역으로 쓰고 싶어서 전역변수 선언
	
	//생성자 db연동하고 맵퍼 연결 Mybatis활용
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {//이거는 서버가 실행할때 같이 켜짐
		this.bsm = sqlSession.getMapper(BoardService_Mapper.class);//getMapper(type)을받는다 인터페이스 Mybatis용 따로 인터페이스를 만든다
	}
	
	
	//게시글 리스트 불러오기
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		ArrayList<BoardVo> blist = bsm.boardSelectAll(scri);//쿼리로 간다
		return blist;
	}

	//게시글 총갯수
	@Override
	public int boardTotal(SearchCriteria scri) {
		int value =	bsm.boardTotal(scri);
		return value;
	}

	//게시글 조회수
	@Override
	public int boardViewCnt(int bidx) {
		int value = bsm.boardViewCnt(bidx);
		return value;
	}

	//게시글 하나보기
	@Override
	public BoardVo boardSelectOne(int bidx) {
		BoardVo bv = bsm.boardSelectOne(bidx);
		return bv;
	}

	//게시글쓰기
	@Override
	public int boardInsert(BoardVo bv) {
		int value = bsm.boardInsert(bv);
		return value;
	}

	//게시글수정
	@Override
	public int boardModify(BoardVo bv) {
		int value = bsm.boardModify(bv);
		return value;
	}


	@Override
	public int boardDelete(BoardVo bv) {
		int value = bsm.boardDelete(bv);
		return value;
	}

	@Transactional //트랜잭션 //둘다성공해야 성공 아니면 롤백
	@Override
	public int boardReply(BoardVo bv) {
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>(); // 키는 String 타입이고 밸류는 Integer이다
		hm.put("originbidx", bv.getOriginbidx()); //해쉬맵에 넣는다 
		hm.put("depth",  bv.getDepth());
		//bsm.boardReplyUpdate(bv.getOriginbidx(), bv.getDepth()); //만약에 값이 타입이 같지않으면 해쉬맵에서 담아서 넘겨야한다
		bsm.boardReplyUpdate(hm);
		int value = bsm.boardReplyInsert(bv);
		
		return value;
	}

}
