package com.logicq.ngr.common.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private static final Logger logger = Logger.getLogger(LoggingAspect.class);

	@Before("execution(* com.logicq.ngr.*.*.*.*(..)) || execution(* com.logicq.ngr.*.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		if (logger.isDebugEnabled()) {
			logger.debug("*********Inside:" + joinPoint.getSignature().getDeclaringType().getName() + "."
					+ joinPoint.getSignature().getName());
		}

	}

	@After("execution(* com.logicq.ngr.*.*.*.*(..)) || execution(* com.logicq.ngr.*.*.*(..))")
	public void logAfter(JoinPoint joinPoint) {
		if (logger.isDebugEnabled()) {
			logger.debug("*********Excuted Successfully :" + joinPoint.getSignature().getDeclaringType().getName() + "."
					+ joinPoint.getSignature().getName());
		}
	}

	@AfterReturning(pointcut = "execution(* com.logicq.ngr.*.*.*.*(..)) || execution(* com.logicq.ngr.*.*.*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		if (logger.isDebugEnabled()) {
			logger.debug("********Method returned value is : " + joinPoint.getSignature().getDeclaringType().getName()
					+ "." + joinPoint.getSignature().getName() + " result :" + result);
		}

	}

	@AfterThrowing(pointcut = "execution(* com.logicq.ngr.*.*.*.*(..)) || execution(* com.logicq.ngr.*.*.*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.error("*********Exception occur : " + joinPoint.getSignature().getDeclaringType().getName() + "."
				+ joinPoint.getSignature().getName() + " Exception :" + error.getMessage(), error);
	}

}