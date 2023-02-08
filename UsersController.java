package com.cronberry.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cronberry.exception_handling.CronberryException;
import com.cronberry.jwtutil.JwtHelpar;
import com.cronberry.model.AuthRequest;
import com.cronberry.model.User;
import com.cronberry.modelDto.UserDTO;
import com.cronberry.repository.UserRepository;
import com.cronberry.service.UserServiceimpl;
import com.cronberry.service.constants.BaseManagerImpl;
import com.cronberry.service.constants.UIConstants;




@RestController
public class UsersController {
	
	@Autowired
	private UserServiceimpl UsersService ;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtHelpar jwtHelpar;
	
	
	
	@PostMapping("cronberry/sign-up")
	public ResponseEntity<Map<String, Object>> createUsers(@Valid @RequestBody UserDTO userDTO)
			throws CronberryException {
		UsersService.createuser(userDTO);
		return BaseManagerImpl.sendSuccessResponse("REGISTER SUCCESS " + userDTO.getName());

	}
	
	@PostMapping("cronberry/sign-in")
	public ResponseEntity<Map<String, Object>> signin(@RequestBody AuthRequest authRequest,
			HttpServletResponse response) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CronberryException(ex.getMessage());
		}
		String token = jwtHelpar.generateToken(authRequest.getUsername());
		String username = jwtHelpar.extractUsername(token);
		User user = UsersService.fetchUserDetailByEmail(username);
		user.setToken(token);
		userRepository.save(user);
		response.setHeader("token", token);
		return BaseManagerImpl.sendSuccessResponse(UIConstants.DATA);

	}
	
	
	@PatchMapping("cronberry/user/change-password")
	public ResponseEntity<Map<String,Object>> changepassword(@RequestBody UserDTO userDTO,Principal principal,HttpServletRequest request,HttpServletResponse response){		
		UsersService.changepassword(userDTO, principal, request, response);
		return BaseManagerImpl.sendSuccessResponse("PASSWORD CHANGE SUCCESS");
		
	}

	@GetMapping("cronberry/user-detail/{email}")
	public ResponseEntity<User> userDetailByEmail(@PathVariable String email){
		return new ResponseEntity<User>(UsersService.fetchUserDetailByEmail(email),HttpStatus.OK);
	}
	
	
	@GetMapping("cronberry/users-list")
	public ResponseEntity<List<UserDTO>> showAllUsersList(){
		return new ResponseEntity<List<UserDTO>>(UsersService.showallusers(), HttpStatus.OK);
		
	}
	
	@GetMapping("cronberry/user/{Usersid}")
	public ResponseEntity<UserDTO> showUsersById(@PathVariable Long Usersid){
		return new ResponseEntity<UserDTO>(UsersService.showByiduser(Usersid), HttpStatus.OK);
	}
	
	@DeleteMapping("cronberry/user/{Usersid}")
	public ResponseEntity<String> deleteUsersById(@PathVariable Long Usersid){
		UsersService.deleteuserbyid(Usersid);
		return new ResponseEntity<String>("delete successfully Users : "+Usersid, HttpStatus.OK);
	}
	@PutMapping("cronberry/user/{Usersid}")
	public ResponseEntity<UserDTO> updateUsersById(@Valid   @RequestBody UserDTO Users,@PathVariable Long Usersid){
		return new ResponseEntity<UserDTO>(UsersService.updateuserbyid(Users, Usersid),HttpStatus.OK);
	}
	

	
	
	
	
	

}
