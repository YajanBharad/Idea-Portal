package com.ideaportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Comments;
import com.ideaportal.models.Ideas;
import com.ideaportal.models.Themes;
import com.ideaportal.models.ThemesCategory;
import com.ideaportal.models.User;
import com.ideaportal.repo.IdeasRepository;
import com.ideaportal.repo.ThemesCategoryRepository;
import com.ideaportal.repo.ThemesRepository;
import com.ideaportal.repo.UserRepository;
import com.ideaportal.repo.commentsRepository;

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
	
	@Autowired
	commentsRepository commRepo;
	
	@Autowired
	IdeasRepository ideasRepository;
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

    	return jdbcTemplate.execute("select user_id from user where user_email_id=?", (PreparedStatementCallback<Integer>) ps -> {
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
    	return jdbcTemplate.execute("select user_id, user_password from user where user_name=?", (PreparedStatementCallback<Integer>) ps -> {
			ps.setString(1, userName);

			ResultSet resultSet=ps.executeQuery();

			if(resultSet.next())
				return 1;
			else
				return -1;
		});
    	
	
    }
    public List<Ideas> buildList(ResultSet resultSet) throws SQLException
	{
		List<Ideas> list=new ArrayList<>();
		
		while(resultSet.next())
		{
			Optional<Ideas> optional=ideasRepository.findById(resultSet.getLong(1));
			list.add(optional.orElse(null));
		}
		return list;
	}
    public ThemesCategory findThemeCategory(long themeCategoryID){
		Optional<ThemesCategory> optionalThemesCategory = themesCategoryRepository.findById(themeCategoryID);

		return optionalThemesCategory.orElse(null);
	}
	
    public List<Themes> buildThemesList(ResultSet resultSet) throws SQLException 
	{
		List<Themes> list=new ArrayList<>();
		while(resultSet.next())
		{
			Optional<Themes> optional=themesRepository.findById(resultSet.getLong(1));
			list.add(optional.orElse(null));
		}
		return list;
	}
    public Ideas isIdeaIDValid(long ideaID)
    {
    	Ideas idea;
    	try
    	{
    		idea=ideasRepository.findById(ideaID).orElse(null);
    	}catch (NoSuchElementException e) {return null;}
    	return idea;
    	
    }
    public Comments buildCommentsObject(Comments comment) 
	{
		Optional<User> optionalUser=userRepo.findById(comment.getUser().getUserId());
		
		User user= optionalUser.orElse(null);
	
		Optional<Ideas> optionalIdeas=ideasRepository.findById(comment.getIdea().getIdeaId());
		
		Ideas idea= optionalIdeas.orElse(null);
		comment.setUser(user);
		comment.setIdea(idea);
	
		return comment;
		
	}
    
    public List<Comments> buildGetCommentsList(ResultSet resultSet) throws SQLException 
	{
		List<Comments> list=new ArrayList<>();
		
		while(resultSet.next())
		{
			Optional<Comments> optional=commRepo.findById(resultSet.getLong(1));
			list.add(optional.orElse(null));
		}
		return list;
		
	}
}
