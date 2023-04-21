package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.MemberVo;



public interface MemberService_Mapper {
	
	//마이바티스의 사용할 메소드를 정의한다
	//public MemberVo memberLogin(String id, String pwd);
	
//회원가입메소드
	public int memberInsert(MemberVo mv);
	//매핑 인터페이스의 구현 객체의 memberInsert 메소드를 호출하면 이때 매퍼xml 파일을 참고해 이 메소드와 쿼리문을 매핑시키게 됩니다.
	//그러면 매퍼 xml에 작성된 쿼리문이 DB에서 실행됩니다.
	//(참고로 Mybatis 환경 설정 파일에도 <mapper> 엘리먼트로 매퍼 xml을 등록하고 사용할 프로퍼티도 미리 선언해야 한다.)
//회원목록불러오기메소드
	public ArrayList<MemberVo> memberList();
//아이디중복체크
	public int memberIdCheck(String memberId);
//로그인	
	public MemberVo memberLogin(String memberId);
	
	
}
