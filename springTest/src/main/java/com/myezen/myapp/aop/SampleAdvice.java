package com.myezen.myapp.aop;



import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //빈으로등록
@Aspect //AOP이다
public class SampleAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Before("execution(* com.myezen.myapp.service.BoardService*.*(..))") //들어가기 전에(경로지정) //Jointpoint가 실행되기 전 실행된다. ( ❗ Advice에서 Jointpoint실행을 제어할 수 없다. )
	public void startLog() {
		
		logger.info("--------------------");
		logger.info("AOP 로그 테스트중 입니다.");
		logger.info("--------------------");
		System.out.println("sysout 입니다.");
	}
	
	
}
