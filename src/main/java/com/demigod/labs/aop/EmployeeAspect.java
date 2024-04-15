package com.demigod.labs.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.demigod.labs.entity.Employee;

/*
 * AOP - Aop works based on interceptors
   Aspect -> Aspects is a class in which we define Pointcuts & Advices.
   Advice -> The methods inside aspect class are called advice.
   JoinPoint -> JoinPoint is the place where the method/advice will execute.
    These are the actual business methods in our application where an Aspect(New behaviour) can be plugged in.
   PointCut -> The expression defines where to execute the advice   
         or At what JoinPoints a given Advice should be applied.
   
    @Before -> Advice will be executed before a JpinPoint but doesn't have the ability to prevent
            the execution flow unless it throws an exception.
    @AfterReturning -> Advice to be executed after a joinpoint completes normally         
    @AfterThrowing -> Advice to be executed if a method exits by throwing an exception         
    @After  ->   Advice to be executed regardless of the result, whether it exits normally or exceptionally
    @Around -> It will get the highest priority over all advice.
*/

@Aspect
@Component
public class EmployeeAspect {

	/*
	 * This Pointcut Expression says Before executing any method in the controller
	 * class execute the below code 1st * -> for all return type 2nd * -> for all
	 * method in the controller class (..) -> for any no. of parameters
	 */

	@Before(value = "execution(* com.demigod.labs.controller.EmployeeController.*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		System.out.println("Request to " + joinPoint.getSignature() + " started at : " + new Date());
	}

	@After(value = "execution(* com.demigod.labs.controller.EmployeeController.*(..))")
	public void afterAdvice(JoinPoint joinPoint) {
		System.out.println("Request to " + joinPoint.getSignature() + " ended at : " + new Date());
	}

	@Before(value = "execution(* com.demigod.labs.service.EmployeeService.*(..))")
	public void beforeAdviceForService(JoinPoint joinPoint) {
		System.out.println("Request to service class " + joinPoint.getSignature() + " started at : " + new Date());
	}

	@After(value = "execution(* com.demigod.labs.service.EmployeeService.*(..))")
	public void afterAdviceForService(JoinPoint joinPoint) {
		System.out.println("Request to service class " + joinPoint.getSignature() + " ended at : " + new Date());
	}

	@AfterReturning(value = "execution(* com.demigod.labs.service.EmployeeService.addEmployee(..))", returning = "employee")
	public void afterReturningAdviceForAddEmpService(JoinPoint joinPoint, Employee employee) {
		System.out.println("Employee saved successfully & the employee id is : " + employee.getId());
	}

	@AfterThrowing(value = "execution(* com.demigod.labs.service.EmployeeService.addEmployee(..))", throwing = "ex")
	public void aroundAdviceForAddEmpService(JoinPoint joinPoint, Exception ex) {
		System.out.println("Business logic to save an employee threw exception : " + ex.getMessage());
	}

	@Around(value = "execution(* com.demigod.labs.service.EmployeeService.addEmployee(..))")
	public Employee afterThrowingAdviceForAddEmpService(ProceedingJoinPoint joinPoint) {
		System.out.println("Inside Around Advice in Aspect : Business logic to save an employee started at : " + new Date());
		try {
			
			// If you want to change the request data then do like below
			// This is helpful during password encryption
			/*
			 * Employee [] empArray = new Employee[1]; 
			 * Employee modifiedEmp = new Employee();
			 * modifiedEmp.setName("Modified Name"); 
			 * empArray[0] = modifiedEmp;
			 * Employee employee = (Employee) joinPoint.proceed(empArray);
			 */
			
			Employee employee = (Employee) joinPoint.proceed(); // This is mandatory to run the actual method call in @Around
			return employee;
		} catch (Throwable e) {
			System.out.println("Inside Around Advice in Aspect : Business logic to save an employee failed : " + e.getMessage());
		}
		System.out.println("Inside Around Advice in Aspect : Business logic to save an employee ended at : " + new Date());
		return null;
	}

}
