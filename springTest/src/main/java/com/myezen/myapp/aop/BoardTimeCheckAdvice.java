package com.myezen.myapp.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //빈등록 // @Aspect 어노테이션을 붙여 이 클래스가 Aspect를 나타내는 클래스라는 것을 명시하고 @Component를 붙여 스프링 빈으로 등록한다.
@Aspect //AOP이다 
public class BoardTimeCheckAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardTimeCheckAdvice.class);//로그
	
	@Around("execution(* com.myezen.myapp.service.BoardService*.*(..))") //어드바이스//@Around	proceed() 메소드 호출 전, 후로 통해 기능 구분 수행 //@Around 어노테이션은 타겟 메서드를 감싸서 특정 Advice를 실행한다는 의미이다. 
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		
		logger.info("Before AOP");
		logger.info(Arrays.toString(pjp.getArgs()));//toString() : 조언되는 방법에 대한 유용한 설명 인쇄//getArgs() : 메서드 인수 반환
		long startTime = System.currentTimeMillis();
		
		result = pjp.proceed();//진행  //proceed()하기 전은 Before 후는 After
		//Around Advice에서 사용할 공통 기능 메서드는 대부분 파라미터로 전달 받은 ProceedingJoinPoint의 proceed() 메서드만 호출하면 된다.
		/*그런데 여기서 proceed() 메소드가 반환하는 값이 있는데 Object이다. 여기에 무엇이 담겨 있단 말인가?
			여기에는 비지니스 메소드가 실행한 후의 결과 값들이 담겨 있게 된다.
			예를 들어 비지니스 메소드의 기능이 select 기능이라고 한다면 그 결과 값(보통은 VO 형태에 담기고 이 VO가 Object에 담기게 된다)이 Object에 담기게 되고 insert와 같이 return 되는 값이 없을 경우에는 Object에는 null이 담기게 된다.*/
		
		long endTime = System.currentTimeMillis();
		logger.info("After AOP");
		logger.info(pjp.getSignature().getName()+":"+(endTime-startTime));//메소드이름을 뽑아냄
		
		return result;
	}
	
	
	
	
	
	
}
