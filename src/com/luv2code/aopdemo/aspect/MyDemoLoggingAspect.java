package com.luv2code.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
	
	//After returning aspect
	
	@AfterReturning(
			pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccount(..))",
			returning="result")
	public void afterReturningFindAccountAdvice(JoinPoint thePoint, List<Account> result) {
		//print which method
		String method = thePoint.getSignature().toShortString();
		System.out.println(">>>>>>>>>>>>Method name"+method);
		
		System.out.println("\n resuult is"+result);
		
		//Modifying data but be careful
		convertAccountNamesToUpperCase(result);
		
		System.out.println("UpperCased>>>>>>>>>>>>>"+result);
	}
	
	private void convertAccountNamesToUpperCase(List<Account> result) {
		for(Account ac : result) {
			String theUpperName = ac.getName().toUpperCase();
			ac.setName(theUpperName);
		}
		
	}

	@Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.combinedGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinpoints) {
		System.out.println("\n=======>>> Excecuting @Before  Advice on method()");
		
		//display signature
		MethodSignature methodSig = (MethodSignature)theJoinpoints.getSignature();
		System.out.println("Method======>"+methodSig);
	
	//display method arguments
		
		// get args
		Object[] args = theJoinpoints.getArgs();

		//loop thru args
		for (Object argus :  args) {
			System.out.println(argus);
			if (argus instanceof Account) {
				//downcast and print specific stuff
				Account theAccount = (Account) argus;
				
				System.out.println("Acount name:"+theAccount.getName());
				System.out.println("Acount level:"+theAccount.getLevel());
			}
		}
	
	}	
}
