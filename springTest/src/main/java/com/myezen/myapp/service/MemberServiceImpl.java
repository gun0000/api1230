package com.myezen.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.persistance.MemberService_Mapper;

@Service("memberServiceImpl") //서비스 용도, 빈으로 등록하겠다 
public class MemberServiceImpl implements MemberService {

	//MyBatis 빈에서 가져오기 
	//2023-04-12
	//@Autowired /*컨테이너의 SqlSession과 동일한 타입 객체 존재할 경우 자동 주입, root-context.xml의 <bean id="sqlSession" ~>에 의해 등록된 상태*/
	//SqlSession sqlSession; /*DB와 연결 상태(세션)을 유지하는 역할을 합니다.*/
							/*DB와 연결만 되어있을뿐, 어떤 sql을 DB에 날릴 지는 결정되어 있지 않기 때문에 SqlSession은 Mapper Interface의 구현 객체를 생성합니다. */
							/*그리고 애플리케이션에서 Mapper Interface의 구현 객체의 메소드를 실행하면 실행 중에 동적으로 매핑파일의 sql 내용을 참고하여 DB에 쿼리를 날리게 됩니다.*/
	//생성자사용 2023-04-13
	private MemberService_Mapper msm;
	@Autowired
	public MemberServiceImpl(SqlSession sqlSession) {
		this.msm = sqlSession.getMapper(MemberService_Mapper.class);
	}
	
	//회원가입메소드
	@Override //MemberService의 메소드 구현
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone,String memberEmail, String memberGender, String memberAddr, String memberBirth) {
		
		//오는변수담기
		MemberVo mv = new MemberVo(); //멤버VO객체를 생성해서 mv에 담는다
		mv.setMemberid(memberId);
		mv.setMemberpwd(memberPwd);
		mv.setMembername(memberName);
		mv.setMemberphone(memberPhone);
		mv.setMemberemail(memberEmail);
		mv.setMembergender(memberGender);
		mv.setMemberaddr(memberAddr);
		mv.setMemberbirth(memberBirth);
		
		//2023-04-12 생성자사용 이전에//MemberService_Mapper msm = sqlSession.getMapper(MemberService_Mapper.class); //persistance에 있는 Mapper클래스를 쓴다
		//그리고 sqlsession.getMapper(MemberService_Mapper.class);를 호출하는데, 이 부분이 매퍼 인터페이스의 구현 클래스를 얻는 과정입니다.
		//MemberService_Mapper는 인터페이스로 다형성에 의해 인터페이스형 참조 변수로 구현 클래스를 가리킬 수 있게 됩니다.
		//그러면 getMapper()에 의해 인터페이스 구현 객체가 생성되었으니 구현 클래스의 메소드에 실제 쿼리문을 매핑시키는 과정만 처리하면 됩니다.
		int value = msm.memberInsert(mv); // mv를 넘긴다
		//매핑 인터페이스의 구현 객체의 memberInsert 메소드를 호출하면 이때 매퍼xml 파일을 참고해 이 메소드와 쿼리문을 매핑시키게 됩니다.
		//그러면 매퍼 xml에 작성된 쿼리문이 DB에서 실행됩니다.
		//(참고로 Mybatis 환경 설정 파일에도 <mapper> 엘리먼트로 매퍼 xml을 등록하고 사용할 프로퍼티도 미리 선언해야 한다.)
		
		return value;
	}
	//회원목록불러오기메소드
	@Override
	public ArrayList<MemberVo> memberList() {
		//2023-04-12 생성자사용 이전에//MemberService_Mapper msm = sqlSession.getMapper(MemberService_Mapper.class);
		ArrayList<MemberVo> alist = msm.memberList();
		
		return alist;
	}
	//아이디중복체크
	@Override
	public int memberIdCheck(String memberId) {
		//2023-04-12 생성자사용 이전에//MemberService_Mapper msm = sqlSession.getMapper(MemberService_Mapper.class);
		int value = msm.memberIdCheck(memberId);
		
		return value;
	}
	//로그인
	@Override
	public MemberVo memberLogin(String memberId) {
		
		MemberVo mv = msm.memberLogin(memberId);
		
		return mv;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
