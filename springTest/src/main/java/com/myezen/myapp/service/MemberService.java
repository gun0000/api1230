package com.myezen.myapp.service;
	//인터페이스

import java.util.ArrayList;

import com.myezen.myapp.domain.MemberVo;

public interface MemberService {
	//사용할 메소드를 쓴다
	
//추상메소드
	//회원가입메소드
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone, String memberEmail, String memberGender, String memberAddr, String memberBirth);
	//회원목록불러오기메소드
	public ArrayList<MemberVo> memberList();
	//아이디중복체크
	public int memberIdCheck(String memberId);
	//로그인
	public MemberVo memberLogin(String memberId);
}
