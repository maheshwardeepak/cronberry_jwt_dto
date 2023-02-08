package com.cronberry.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cronberry.exception_handling.CronberryException;
import com.cronberry.modelDto.EmailOTPDTO;
import com.cronberry.modelDto.UserDTO;
import com.cronberry.service.UserServiceimpl;
import com.cronberry.service.constants.UIConstants;

@RestController
public class TestController {

	
	@Autowired(required = true)
	private  UserServiceimpl userServiceimpl;
    
	
	
	
	@PostMapping(value = "/request-email-otp", produces = MediaType.APPLICATION_JSON_VALUE)
	public EmailOTPDTO requestEmailOTP(@RequestBody EmailOTPDTO emailOtpDTO,
			HttpServletRequest request) throws CronberryException {
		emailOtpDTO.setUserid((Long) request.getAttribute(UIConstants.USER_ID));		
		return userServiceimpl.requestEmailOTP(emailOtpDTO);
	}
	
	

	@PostMapping(value = "/verify-email-otp", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> verifyEmailOTP(@RequestBody EmailOTPDTO emailOtpDTO,
			HttpServletRequest request) throws CronberryException {
		emailOtpDTO.setUserid((Long) request.getAttribute(UIConstants.USER_ID));
		return userServiceimpl.verifyEmailOTP(emailOtpDTO);
	}
	
	
	
	
	
//	
//	@PostMapping(value = "/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Boolean forgotPassword(@RequestBody UserDTO usersDTO)
//			throws CronberryException {
//		return userServiceimpl.forgotPassword(usersDTO);
//	}
//
//	@PostMapping(value = "/is-link-expired", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Map<String, Object> isLinkExpired(@RequestBody UserDTO usersDTO) throws CronberryException {
//		return userServiceimpl.isLinkExpired(usersDTO);
//	}
//
//	@PostMapping(value = "/is-link-expired-forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Map<String, Object> isLinkExpiredForgotPassword(@RequestBody UserDTO usersDTO)
//			throws CronberryException {
//		return userServiceimpl.isLinkExpiredForgotPassword(usersDTO);
//	}
//
//	@PostMapping(value = "/update-new-password", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Boolean updateNewPassword(@RequestBody UserDTO usersDTO,
//			HttpServletRequest request) throws CronberryException {
//		return userServiceimpl.updateNewPassword(usersDTO);
//	}

	


}
