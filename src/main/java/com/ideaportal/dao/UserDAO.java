package com.ideaportal.dao;



import java.sql.ResultSet;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import com.ideaportal.exception.InvalidRoleException;
import com.ideaportal.models.Comments;
import com.ideaportal.models.Ideas;
import com.ideaportal.models.Likes;
import com.ideaportal.models.Login;
import com.ideaportal.models.ParticipationResponse;
import com.ideaportal.models.ResponseMessage;
import com.ideaportal.models.Roles;
import com.ideaportal.models.Themes;
import com.ideaportal.models.ThemesCategory;
import com.ideaportal.models.User;
import com.ideaportal.repo.IdeasRepository;
import com.ideaportal.repo.LikeRepository;
import com.ideaportal.repo.ParticipationRepository;
import com.ideaportal.repo.ThemesCategoryRepository;
import com.ideaportal.repo.UserRepository;
import com.ideaportal.repo.commentsRepository;
@Repository
public class UserDAO {
	
	@Autowired
	commentsRepository commrepo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired

	IdeasRepository ideasRepository;
	
	@Autowired
	LikeRepository likeRepository;

	@Autowired
	DaoUtils utils;

	@Autowired
	ParticipationRepository partRepo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ThemesCategoryRepository themeCatRepo;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class); 
	
	 public User saveUser(User userDetails) 
	    {
		 	LOG.info("Checking if Role No is correct");
	    	Roles role=userDetails.getRoles();
	    	
	    	int roleID=role.getRoleId();
	    	
	    	if(roleID!= 1 && roleID != 2 && roleID!= 3) {
	    		LOG.error("Invalid Role Id only 1,2,3 allowed");
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
		   public List<Ideas> getAllIdeas(long themeid) {

				return jdbcTemplate.execute("select idea_id from Ideas where theme_id = ?", (PreparedStatementCallback<List<Ideas>>) ps -> {
					ps.setLong( 1,themeid);
					ResultSet resultSet=ps.executeQuery();
					
					return utils.buildList(resultSet);
				});
		   }
		   
		   public Comments saveComment(Comments comment) 
			{
				comment.setCommentDate(Timestamp.valueOf(LocalDateTime.now()));
				comment=utils.buildCommentsObject(comment);
				commrepo.save(comment);
				return comment;
			}
		   
		   public List<Comments> getCommentsList(long ideaID)
			{
				return jdbcTemplate.execute("select comment_id from comments where idea_id=?", (PreparedStatementCallback<List<Comments>>) ps -> {
					ps.setLong(1, ideaID);

					ResultSet resultSet=ps.executeQuery();

					return utils.buildGetCommentsList(resultSet);
				});
			}
		   
		   public Likes saveLikes(Likes likes)
			{
				long likeID=utils.findLikeID(likes.getIdea().getIdeaId(), likes.getUser().getUserId());
				
				likes.setLikedDate(Timestamp.valueOf(LocalDateTime.now()));

				if (likeID != 0) {
					likes.setLikeId(likeID);
				}
				
				likes=utils.buildLikesObject(likes);
				likes=likeRepository.save(likes);
				
				return likes;
			}
		   public List<User> getLikesForIdeaList(long ideaID)
			{
				return jdbcTemplate.execute("select user_id from Likes where idea_id=? and like_value=?", (PreparedStatementCallback<List<User>>) ps -> {
					ps.setLong(1, ideaID);
					ps.setLong(2, 1);

					ResultSet resultSet=ps.executeQuery();

					return utils.buildUserList(resultSet);

				});
			}

		   public List<User> getDislikesForIdeaList(long ideaID)
			{
				return jdbcTemplate.execute("select user_id from Likes where idea_id=? and like_value=?", (PreparedStatementCallback<List<User>>) ps -> {
					ps.setLong(1, ideaID);
					ps.setLong(2, 0);
					ResultSet resultSet=ps.executeQuery();

					return utils.buildUserList(resultSet);

				});
					
			}//Dislike Value

		   public ParticipationResponse enrollResponse(ParticipationResponse participant)
			{
			    long uId=utils.findIfParticipated(participant.getUser().getUserId(),participant.getIdea().getIdeaId());
			    if(uId==0L)
			    {	
				participant.setParticipationDate(Timestamp.valueOf(LocalDateTime.now()));
				participant=utils.buildParticipantObject(participant);
				partRepo.save(participant);
				return participant;
			    }
			    else
			    {
			    	return null;
			    }
			}
		   public List<User> getParticipantList(long ideaId)
			{
				return jdbcTemplate.execute("select user_id from participationresponse where idea_id=?", (PreparedStatementCallback<List<User>>) ps -> {
					ps.setLong(1, ideaId);


					ResultSet resultSet=ps.executeQuery();

					return utils.buildUserList(resultSet);


				});
			}

		   public ResponseMessage<ThemesCategory> addCategory(ThemesCategory themeCategory)
		    {
			 	themeCategory=themeCatRepo.save(themeCategory);
			 	ResponseMessage<ThemesCategory> responseMessage=new ResponseMessage<>();
		        responseMessage.setResult(themeCategory);
		        responseMessage.setStatus(HttpStatus.CREATED.value());
		        responseMessage.setStatusText("Theme Category created Sucessfully");	
		        responseMessage.setTotalElements(1);
		        return responseMessage;
		        		
		    }
		   public List<ThemesCategory> getThemeCategories()
			{
				return jdbcTemplate.execute("select * from themescategory", (PreparedStatementCallback<List<ThemesCategory>>) ps -> {
					
					ResultSet resultSet=ps.executeQuery();

					return utils.buildCategoriesList(resultSet);

				});
			}
		   public List<Ideas> getIdeasByMostLikesList(long themeID)
			{
				
				return jdbcTemplate.execute("select Ideas.idea_id, SUM(Likes.like_value) from Ideas left outer join Likes on Likes.idea_id=Ideas.idea_id where theme_id= ? GROUP BY(idea_id) ORDER BY SUM(Likes.like_value) DESC, Ideas.idea_id ASC", (PreparedStatementCallback<List<Ideas>>) ps -> {
					ps.setLong(1, themeID);

					ResultSet rSet=ps.executeQuery();

					return utils.buildList(rSet);
				});
			}
		   public List<Ideas> getIdeasByMostCommentsList(long themeID) 
			{
				return jdbcTemplate.execute("select Ideas.idea_id, COUNT(Comments.comment_value) from Ideas left outer join Comments on Comments.idea_id=Ideas.idea_id where theme_id=?  GROUP BY (idea_id) ORDER BY COUNT(Comments.comment_value) DESC, Ideas.idea_id ASC", (PreparedStatementCallback<List<Ideas>>) ps -> {
					ps.setLong(1, themeID);

					ResultSet resultSet=ps.executeQuery();

					return utils.buildList(resultSet);

				});
			}
		   public User updatePassword(User userDetail) 
		    {
		        User user = null;
		        
		        try
		        {
		        	user=userRepository.findById(userDetail.getUserId()).orElse(null);
		        }catch(NoSuchElementException e) {
		        	return user;
		        }

		        String hashedUserPassword = BCrypt.hashpw(userDetail.getUserPassword(), BCrypt.gensalt(10));

		        if (user != null) {
					user.setUserPassword(hashedUserPassword);

					return userRepository.save(user);
				}
		        else 
		        	return user;
		    }

}
