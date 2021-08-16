package com.ideaportal.dao;

import java.sql.ResultSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Themes;
import com.ideaportal.models.ThemesCategory;
import com.ideaportal.models.User;
import com.ideaportal.repo.ThemesCategoryRepository;
import com.ideaportal.repo.ThemesRepository;
import com.ideaportal.repo.UserRepository;

@Repository
public class DaoUtils {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ThemesRepository themesRepository;
	@Autowired
	ThemesCategoryRepository themesCategoryRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	public User findByUserId(final long userID) {
        User user = null;
        try {
            user = this.userRepo.findById(userID).orElse(null);
        }
        catch (NoSuchElementException exception) {
            return user;
        }
        return user;
    }
    public Themes findThemeByID( long themeID) {

		try
		{
			Themes themes = themesRepository.findById(themeID).orElse(null);
			
			return themes;
		}catch(NoSuchElementException e) {return null;}
    }
    public int getCountByEmail(String userEmail) 
    {

    	return jdbcTemplate.execute("select user_id from User where user_email=?", (PreparedStatementCallback<Integer>) ps -> {
			ps.setString(1, userEmail);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next())
				return 1;
			else
				return -1;
		});
    	
    }
    
    //Performs select operation and returns number of user based on the userName
    public int getCountByUserName(String userName)
    {
    	return jdbcTemplate.execute("select user_id, user_password from User where user_name=?", (PreparedStatementCallback<Integer>) ps -> {
			ps.setString(1, userName);

			ResultSet resultSet=ps.executeQuery();

			if(resultSet.next())
				return 1;
			else
				return -1;
		});
    	
	
    }
    public ThemesCategory findThemeCategory(long themeCategoryID){
		Optional<ThemesCategory> optionalThemesCategory = themesCategoryRepository.findById(themeCategoryID);

		return optionalThemesCategory.orElse(null);
	}
	
}
