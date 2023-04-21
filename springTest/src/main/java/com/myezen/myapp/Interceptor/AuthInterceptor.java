package com.myezen.myapp.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AuthInterceptor extends HandlerInterceptorAdapter {// 인터셉터  servlet-context.xml에서 빈으로 띄운다 그리고 경로도 지정해준다

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)//preHandle는 핸들러가 실행하기 전에 동작한다
			throws Exception {
		//인증
		HttpSession session = request.getSession();
		if (session.getAttribute("midx")==null) {//로그인을 하지않았으면 
			//로그인후 이동할 주소를 담는다
			saveDest(request);
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
		}
		return true;
	} 
	
	private void saveDest(HttpServletRequest request) {//매개변수로 request객체를 받는다 
		
		String root = request.getContextPath();//앞에있는 주소 뽑기 그리구 substring으로 짜르기
		String uri = request.getRequestURI().substring(root.length());//주소뽑기
		String query = request.getQueryString();//쿼리뽑기 둘다 뽑으면 풀경로 ?a=b
		if (query == null || query.equals("null")) {//만약에 쿼리값이 NULL이면 쿼리에 ""를 넣어주라 아니면 ?를 추가해줘라 
			query = "";
		}else {
			query = "?"+query;
		}
		
		if (request.getMethod().equals("GET")) { //만약에 요청받은 값이 GET방식이면 밑에를 실행하라 
			request.getSession().setAttribute("dest", uri+query);//dest로 담는다 MemberController에서 dest세션을 꺼내온다
		}
	}
	
	
}
