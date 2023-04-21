package com.myezen.myapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.service.MemberService;

@Controller    /*▷ 작성하는 코드가 컨트롤러인지 어떻게 파악할까? @Controller 어노테이션으로 인해 이 클래스가 컨트롤러인지 파악한다. ▷ 컨트롤러가 페이지가 어딨는지 어떻게 알고 연결할까?servlet-context.xml 파일에 가보면 컨트롤러 어노테이션과 어떻게 주소를 매핑해서 뷰를 연결할건지 등등 컨트롤러에 대한 설정이 들어있다. https://luanaeun.tistory.com/196*/
@RequestMapping(value = "/member") /*@RequestMapping() :  요청 주소와 실제주소를 매핑하는 어노테이션. value=""는 요청받을 url을 설정하게 된다.method=""는 어떤 요청으로 받을지 정의하게 된다.(GET, POST, PUT, DELETE 등) */
public class MemberController {
	
	@Autowired //주입받는다, 빈으로 생성한것을 객체로 생성했으니까 MemberService타입을 찾아서 ms에 주입해주세요 //IoC 컨테이너에 빈으로 등록이 되어야 의존성 주입을 할 수 있다.//필요한 의존 객체의 “타입"에 해당하는 빈을 찾아 주입한다.
	MemberService ms; //다형성을 이용하여 MemberService(인터페이스)->를 구현한 MemberServiceImpl.java //DAO같은
	
	@Autowired //비밀번호 암호화 //빈을 찾아서 주입
	private BCryptPasswordEncoder bcryptPasswordEncoder; //BCryptPasswordEncoder란 Spring Security에서 제공하는 클래스 중 하나로, 암호화하는 데 사용할 수 있는 메소드를 가진 클래스다.
														 //Spring - SpringSecurity 사용해서 패스워드 암호화 하기 https://5bong2-develop.tistory.com/257
	
//!회원가입페이지
	@RequestMapping(value = "/memberJoin.do")//get방식 post방식으로 받을수 있다 그 속성을 활용하지않으면 둘다 받는다
	public String memberJoin() {
		
		return "member/memberJoin"; 
	}
	//!회원가입페이지에서 회원가입버튼누르면
		@RequestMapping(value = "/memberJoinAction.do")
		public String memberJoinAction(
				@RequestParam("memberId") String memberId, /*url이 전달될 때 name 파라미터(name에 담긴 value)를 받아오게 됩니다. 즉, @RequestParam("실제 값") String 설정할 변수 이름*/
				@RequestParam("memberPwd") String memberPwd,
				@RequestParam("memberName") String memberName,
				@RequestParam("memberPhone") String memberPhone,
				@RequestParam("memberEmail") String memberEmail,
				@RequestParam("memberGender") String memberGender,
				@RequestParam("memberAddr") String memberAddr,
				@RequestParam("memberBirth") String memberBirth
				) {
				
				String memberPwd2 = bcryptPasswordEncoder.encode(memberPwd);//비밀번호 암호화 이 인터페이스에는 평문인 비밀번호를 암호화하는 encode(), 평문 비밀번호를 인코딩(암호화)된 비밀번호와 비교하는 matches()가 존재한다.
				int value = ms.memberInsert(memberId, memberPwd2, memberName, memberPhone, memberEmail, memberGender, memberAddr, memberBirth);
				/*MemberService(인터페이스)->를 구현한 MemberServiceImpl.java의 memberInsert메소드로 들어가서 쿼리작동*/
				/*MemberService(인터페이스)->를 구현한 MemberServiceImpl.java->MemberService_Mapper(인터페이스)->mybatis_config.xml->MemberService_Mapper.xml*/
			return "redirect:/"; // /루트로 보낸다
		}
//!회원목록으로 이동
	@RequestMapping(value = "/memberList.do")
	public String memberList(Model model) {//모델 클래스 
		
		ArrayList<MemberVo> alist = ms.memberList();
		
		model.addAttribute("alist", alist);//모델에 alist를 담는다	request.setAttribute("alist", alist); 처럼
		
		return "member/memberList";
	}
//!아이디중복체크
	@ResponseBody //객체로 보내주겠다 return을
	@RequestMapping(value = "/memberIdCheck.do")//ajax로 보낸다 memberIdCheck.do 경로로 들어온다
	public String memberIdCheck(@RequestParam("memberId") String memberId) {
		String str = null;
		int value = 0;
		value = ms.memberIdCheck(memberId);//아이디 중복체크 메소드
		str = "{\"value\": \""+value+"\"}";//json형식
		return str; //json파일 형태로 보내준다 //객체를 돌려준다
	}
//!로그인페이지로 이동
	@RequestMapping(value = "/memberLogin.do")
	public String memberLogin() {
		
		return "member/memberLogin"; //로그인 페이지로 이동
	}
	//!로그인페이지에서 로그인버튼 클릭
		@RequestMapping(value = "/memberLoginAction.do")
		public String memberLoginAction(
				@RequestParam("memberId") String memberId, /*url이 전달될 때 name 파라미터(name에 담긴 value)를 받아오게 됩니다. 즉, @RequestParam("실제 값") String 설정할 변수 이름*/
				@RequestParam("memberPwd") String memberPwd,
				HttpSession session,//http세션 가지고오기
				RedirectAttributes rttr// 1회성으로 한번만 뿌리면 다시 안나타난다 란 기능을 사용하기위해 RedirectAttributes사용 //addAttribute는 값을 지속적으로 사용해야할때 addFlashAttribute는 일회성으로 사용해야할때 사용해야합니다.
				) {
			
			MemberVo mv = ms.memberLogin(memberId);
			String path = "";					
			
													//암호화//평문 비밀번호를 인코딩(암호화)된 비밀번호와 비교하는 matches(가져온비밀번호,db에있는비밀번호)
			if (mv != null && bcryptPasswordEncoder.matches(memberPwd, mv.getMemberpwd())) {
				
				//session.setAttribute("midx", mv.getMidx()); //세션에 담기
				//session.setAttribute("memberName", mv.getMembername()); //세션에 담기
				
				rttr.addAttribute("midx", mv.getMidx());//LoginInterceptor로 보내기 
				rttr.addAttribute("memberName", mv.getMembername());//LoginInterceptor로 보내기 
				
				if (session.getAttribute("dest") == null) { //AuthInterceptor에 dust세션 인터셉터 추가 
					path = "redirect:/";
				}else {
					String dest = (String)session.getAttribute("dest");
					path ="redirect:"+dest;					
				}
				
				
			}else {//비밀번호가 다를때
				rttr.addFlashAttribute("msg", "아이디와 비밀번호를 확인해주세요.");//addFlashAttribute는 일회성으로 한번 사용하면 Redirect후 값이 소멸됩니다.
				path ="redirect:/member/memberLogin.do";
			}
			return path;// if else문에서 경로를 담아서 보내준다
		}
//!로그아웃 클릭
	@RequestMapping(value = "/memberLogOut.do")
	public String memberLogOut(HttpSession session) {
		session.removeAttribute("midx");
		session.removeAttribute("memberName");
		session.invalidate();//세션을 사라지게 초기화
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
