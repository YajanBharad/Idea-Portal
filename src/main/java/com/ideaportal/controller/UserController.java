package com.ideaportal.controller;




import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideaportal.dao.DaoUtils;
import com.ideaportal.dao.UserDAO;
import com.ideaportal.dto.UserDTO;

import com.ideaportal.exception.UserAuthException;
import com.ideaportal.models.Login;
import com.ideaportal.models.ResponseMessage;

import com.ideaportal.models.User;
import com.ideaportal.services.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserController {

	
	final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	  ModelMapper modelMapper;
	  @Autowired
	    UserDAO userDAO;
	  @Autowired
	  UserService userService;
	    @Autowired
	    DaoUtils utils;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<ResponseMessage<User>> createNewUser(@RequestBody UserDTO userDTO)
	{
        
         User userDetails = modelMapper.map(userDTO, User.class);
         System.out.println(userDetails);
        ResponseMessage<User> responseMessage = userService.addUser(userDetails);
      
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
	
	@PostMapping(value = "/login")
	public ResponseEntity<ResponseMessage<User>> loginUser(@RequestBody Login userDetails) throws UserAuthException 
	{
	   
         
        ResponseMessage<User> responseMessage = userService.checkCredentials(userDetails);
       

		return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));

    }
	
	
}
