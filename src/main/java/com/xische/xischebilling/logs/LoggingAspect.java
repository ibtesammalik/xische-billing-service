package com.xische.xischebilling.logs;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

	

	 
	 @Around("(@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || " +
	            "@annotation(org.springframework.web.bind.annotation.PatchMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping))"
	            + "&& @annotation(com.xische.xischebilling.logs.LogExecution)")
	 public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	     //   String methodName = joinPoint.getSignature().getName();

	      
	        Object[] args = joinPoint.getArgs();
	        log.info("+++++++++++++++******************* Parmaters info :"+joinPoint.getSignature()+"++++++++++++++++++**************" );
	        log.error("+++++++++++++++******************* Parmaters error:"+joinPoint.getSignature()+"++++++++++++++++++**************" );
	        for (int argIndex = 0; argIndex < args.length; argIndex++) {
	        	 log.info( args[argIndex]+"" );
	        	 log.error( "error:::::::: "+args[argIndex]+"" );
	            }
	        
	        log.info("+++++++++++++++******************* End Parmaters info :"+joinPoint.getSignature()+"++++++++++++****************" );
	        log.error("+++++++++++++++******************* End Parmaters error:"+joinPoint.getSignature()+"++++++++++++****************" );
	        Object result = joinPoint.proceed();
	       
	        log.info("+++++++++++++++++++++Response info:"+ joinPoint.getSignature()+" ++++++++++++++++++ :::::"+result);
	        log.error("+++++++++++++++++++++Response error:"+ joinPoint.getSignature()+" ++++++++++++++++++ :::::"+result);
	        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	        String json = ow.writeValueAsString(result);
	        log.info("+++++++++++++++++++++Response info:"+joinPoint.getSignature()+" ++++++++++ In JSON FORM ++++++++ :::::"+json);
	        log.error("+++++++++++++++++++++Response error:"+ joinPoint.getSignature()+" ++++++++In JSON FORM ++++++++++ :::::"+json);
	        return result;
	    }
}
