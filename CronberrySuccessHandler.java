package com.cronberry.exception_handling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cronberry.service.constants.UIConstants;

@RestControllerAdvice
public class CronberrySuccessHandler {
	
	
	
	
	@ExceptionHandler(CronberrySuccess.class)
	public ResponseEntity<Map<String,Object>> cronberrySucessExceptionHandler(CronberrySuccess ex ){
		Map<String, Object> map=new HashMap<>();
		map.put(UIConstants.STATUS, UIConstants.FALSE);
		map.put("message", ex.getMessage());
		map.put(UIConstants.EMPTY_DATA, "");
		map.put(UIConstants.WEB_VERSION, UIConstants.WEB_VERSION_CODE);
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
