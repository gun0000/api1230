package com.myezen.myapp.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {// 인터셉터  servlet-context.xml에서 빈으로 띄운다 그리고 경로도 지정해준다

	@Override //후에
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		//멤버컨트롤러에 로그인부분 로그인이 끝나면 여기서 받음
		Object midx = modelAndView.getModel().get("midx");//컨트롤에서 가져온다  
		Object memberName = modelAndView.getModel().get("memberName");
		
		if (midx != null) {
			request.getSession().setAttribute("midx", midx);//세션에 담기
			request.getSession().setAttribute("memberName", memberName);
		}
	}

	@Override //전에
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("midx") != null) {
			session.removeAttribute("midx");//세션지우기
			session.removeAttribute("memberName");
			session.invalidate();//세션초기화
		}
		
		return true;
	}

	
	
	
	
	
	
	
}
