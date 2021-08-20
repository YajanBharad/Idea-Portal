package com.ideaportal.dao;



import java.sql.ResultSet;
import java.util.NoSuchElementException;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.ideaportal.exception.InvalidRoleException;
import com.ideaportal.models.Ideas;
import com.ideaportal.models.Login;
import com.ideaportal.models.Roles;
import com.ideaportal.models.Themes;
import com.ideaportal.models.User;
import com.ideaportal.repo.IdeasRepository;
import com.ideaportal.repo.UserRepository;
@Repository
public class UserDAO {
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired

	IdeasRepository ideasRepository;

	DaoUtils utils;

	
	@Autowired
	
	JdbcTemplate jdbcTemplate;
	
	 public User saveUser(User userDetails) 
	    {

	    	Roles role=userDetails.getRoles();
	    	
	    	int roleID=role.getRoleId();
	    	
	    	if(roleID!= 1 && roleID != 2 && roleID!= 3) {
	    		
				throw new InvalidRoleException("Invalid Role id was passed");
			}
	    	
	    	if(roleID==1)
	    		role.setRoleName("Client Partner");
	    	if(roleID==2)
	    		role.setRoleName("Product Manager");
	    	if(roleID==3)
	    		role.setRoleName("Participant");
	    	
	        String hashedUserPassword = BCrypt.hashpw(userDetails.getUserPassword(), BCrypt.gensalt(10));
	        userDetails.setUserPassword(hashedUserPassword);
	        userDetails = userRepository.save(userDetails);
			
	        return userDetails;
	    }
	 public User isLoginCredentialsValid(Login userDetails)
	    {
	    	return jdbcTemplate.execute("select user_id, user_password from user where user_name=?", (PreparedStatementCallback<User>) ps -> {
				ps.setString(1, userDetails.getUserName());

				ResultSet resultSet=ps.executeQuery();

				if(resultSet.next() && BCrypt.checkpw(userDetails.getUserPassword(), resultSet.getString(2))) {
					return userRepository.findById(resultSet.getLong(1)).orElse(null);
				}
				return null;
			});
	    }
	 public Ideas getIdea(long ideaID) 
		{
			try
			{
				return ideasRepository.findById(ideaID).orElse(null);
			}catch(NoSuchElementException e) {return null;}
		}
	 

	 //Executes a select * query on themes table 
		public List<Themes> getAllThemesList()
		{
			return jdbcTemplate.execute("select * from themes", (PreparedStatementCallback<List<Themes>>) ps -> {

				ResultSet rSet=ps.executeQuery();

				return utils.buildThemesList(rSet);
			});
		}
}
