package com.ideaportal.services;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ideaportal.dao.DaoUtils;
import com.ideaportal.dao.UserDAO;
import com.ideaportal.exception.InvalidRoleException;
import com.ideaportal.exception.UserAuthException;
import com.ideaportal.models.Comments;
import com.ideaportal.models.Ideas;
import com.ideaportal.models.Likes;
import com.ideaportal.models.Login;
import com.ideaportal.models.ParticipationResponse;
import com.ideaportal.models.ResponseMessage;
import com.ideaportal.models.Themes;
import com.ideaportal.models.ThemesCategory;
import com.ideaportal.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class UserService {
	
	@Autowired
	DaoUtils utils;
	@Autowired
	UserDAO userDAO;
	
	@Value("${ideaportal.jwt.secret-key}")
	public String jwtSecretKey;

	@Value("${ideaportal.jwt.expiration-time}")
	public long jwtExpirationTime;
	
	
	  public String generateJWT(User user) 
	    {
	        long timestamp = System.currentTimeMillis(); //current time in milliseconds
	        return Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtSecretKey)
	                .setIssuedAt(new Date(timestamp))
	                .setExpiration(new Date(timestamp + jwtExpirationTime))
					.claim("user", user)
	                .compact(); //builds the token
	    }
	public boolean saveFile(final MultipartFile file, final File dir) {
        final String filename = file.getOriginalFilename();
        final String path = dir + File.separator + filename;
        final Path filePath = Paths.get(path, new String[0]);
        try {
            final InputStream fileInputStream = file.getInputStream();
            Files.copy(fileInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        catch (IOException e) {
           
            return false;
        }
    }
	 public ResponseMessage<User> addUser(User userDetails)throws InvalidRoleException, IllegalArgumentException
	    {
	    

	        int emailCount = utils.getCountByEmail(userDetails.getUserEmailId());	//checks whether email is already registered or not
	        int userNameCount=utils.getCountByUserName(userDetails.getUserName()); //	checks whether user name is already registered or not
	        
	        ResponseMessage<User> responseMessage=new ResponseMessage<>();
	        
	        if(userNameCount>0) {
	        	
	        	throw new UserAuthException("User Name already is in use");
			}

	        if (emailCount > 0) {
				
	        	throw new UserAuthException("User Email already in use");
			}
	        
	      
	        User user = userDAO.saveUser(userDetails);

	        responseMessage.setResult(user);			//Returns the user object that is saved in the database
	        responseMessage.setStatus(HttpStatus.CREATED.value());
	        responseMessage.setStatusText("signup Sucessfully");
	        responseMessage.setToken(generateJWT(user));			//Passes the generated JWT
	        responseMessage.setTotalElements(1);
	        return responseMessage;
	        		
	    }
	 public ResponseMessage<User> checkCredentials(Login userDetails) 
	    {
	    	User user=userDAO.isLoginCredentialsValid(userDetails);		//Checks whether valid credentials are passes or not
	    	
	    	if(user==null) {
	    		
				throw new UserAuthException("Invalid credentials");
			}
	        ResponseMessage<User> responseMessage =new ResponseMessage<>();
	        
	        responseMessage.setResult(user);		//Returns the object retrieved from the database
	        responseMessage.setStatus(HttpStatus.OK.value());
	        responseMessage.setStatusText("login Sucessfully");
	        responseMessage.setToken(generateJWT(user));				//Generates JWT
	        
	        return responseMessage;
	    }
	 public ResponseMessage<Ideas> getIdeaByIDResponseMessage(long ideaID) 
		{
			ResponseMessage<Ideas> responseMessage=new ResponseMessage<>();
			
			Ideas idea=userDAO.getIdea(ideaID);
			if(idea==null)
			{
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.NOT_FOUND.value());
				responseMessage.setStatusText("Idea not found");

			}
			else
			{
				responseMessage.setResult(idea);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("founded");
				responseMessage.setTotalElements(1);

			}
			return responseMessage;

		}

	 //Service to get all the themes submitted by client partners
		public ResponseMessage<List<Themes>> getAllThemesResponseMessage() 
		{
			List<Themes> list=userDAO.getAllThemesList();
			
			ResponseMessage<List<Themes>> responseMessage=new ResponseMessage<>();
			
			int size=list.size();
			
			if(size==0)
			{
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("OOPS!! No Themes been uploaded by Client Partner.Please try again later!!");

			}
			else
			{
				responseMessage.setResult(list);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("List of all themes");
				responseMessage.setTotalElements(size);
			}
			return responseMessage;
		}
		public ResponseMessage<List<Ideas>> getIdeasBythemeid(long themeID) 
		{
			ResponseMessage<List<Ideas>> responseMessage=new ResponseMessage<>();

			List<Ideas> list=userDAO.getAllIdeas(themeID);

			if(list.isEmpty())
			{
				
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("no idea submitted");
			}
			else
			{
				responseMessage.setResult(list);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("List all ideas");
				responseMessage.setTotalElements(list.size());
			}
			return responseMessage;
		}
		
//		Service to comment on an idea
		public ResponseMessage<Comments> commentAnIdeaResponseMessage(Comments comment) throws Exception
		{
			Comments dbComment=userDAO.saveComment(comment);
			
			if(dbComment==null) {
				throw new Exception("Some error occurred, Please try again");
			}
			ResponseMessage<Comments> responseMessage=new ResponseMessage<>();
			responseMessage.setResult(dbComment);
			responseMessage.setStatus(HttpStatus.CREATED.value());
			responseMessage.setStatusText("Your comment was added");
			responseMessage.setTotalElements(1);
			return responseMessage;
		}
	
		public ResponseMessage<List<Comments>> getCommentForIdeaResponseMessage(long ideaID) 
		{
			List<Comments> list=userDAO.getCommentsList(ideaID);
			
			ResponseMessage<List<Comments>> responseMessage=new ResponseMessage<>();
			
			int size=list.size();
			if(size==0)
			{
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("No Comments to the idea yet");

			}
			
			else
			{
				responseMessage.setResult(list);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("List of all Comments");
				responseMessage.setTotalElements(size);

			}
			return responseMessage;

		}
		public ResponseMessage<Likes> likeAnIdeaResponseMessage(Likes likes) throws Exception
		{
			Likes like =userDAO.saveLikes(likes);
			
			ResponseMessage<Likes> responseMessage=new ResponseMessage<>();
			
			if(like==null) {
				
				throw new Exception("idea was not liked");
			}
			else
			{
				responseMessage.setResult(like);
				responseMessage.setStatus(HttpStatus.CREATED.value());
			
				if(!(like.getLikeValue()))
					responseMessage.setStatusText("dislike idea success");
				else
					responseMessage.setStatusText("like idea success");
				responseMessage.setTotalElements(1);
			}
			
			return responseMessage;
		}
		public ResponseMessage<List<User>> getLikesForIdeaResponseMessage(long ideaID) 
		{
			List<User> list=userDAO.getLikesForIdeaList(ideaID);
			
			ResponseMessage<List<User>> responseMessage=new ResponseMessage<>();
			
			int size=list.size();
			
			if(size==0)
			{
				
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("NO LIKES");
			}
			else
			{
				responseMessage.setResult(list);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("LIKES_LIST");
				responseMessage.setTotalElements(size);

			}
			return responseMessage;

		}

		public ResponseMessage<List<User>> getDislikesForIdeaResponseMessage(long ideaID) 
		{
			List<User> list=userDAO.getDislikesForIdeaList(ideaID);
			ResponseMessage<List<User>> responseMessage=new ResponseMessage<>();
			
			int size=list.size();
			
			if(size==0)
			{
				
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("NO Dislikes");
			}
			else
			{
				responseMessage.setResult(list);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("Dislike list");
				responseMessage.setTotalElements(size);

			}
			return responseMessage;

		}
			
			
		public ResponseMessage<ParticipationResponse> enrollParticipant(ParticipationResponse participant) throws Exception
		{
			ParticipationResponse partresponse=userDAO.enrollResponse(participant);
			
			if(partresponse==null) {
				ResponseMessage<ParticipationResponse> responseMessage=new ResponseMessage<>();
				responseMessage.setResult(partresponse);
				responseMessage.setStatus(HttpStatus.CREATED.value());
				responseMessage.setStatusText("You have already Participated for this Idea");
				responseMessage.setTotalElements(1);
				return responseMessage;
			}
			ResponseMessage<ParticipationResponse> responseMessage=new ResponseMessage<>();
			responseMessage.setResult(partresponse);
			responseMessage.setStatus(HttpStatus.CREATED.value());
			responseMessage.setStatusText("Your participation is considered");
			responseMessage.setTotalElements(1);
			return responseMessage;
		}
		
		public ResponseMessage<List<User>> getParticipantsForIdea(long ideaId) 
		{
			List<User> list=userDAO.getParticipantList(ideaId);

			
			ResponseMessage<List<User>> responseMessage=new ResponseMessage<>();
			
			int size=list.size();
			
			if(size==0)
			{
				
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("NO Participants");

			}
			else
			{
				responseMessage.setResult(list);
				responseMessage.setStatus(HttpStatus.OK.value());

				responseMessage.setStatusText("Participant's List");

				responseMessage.setTotalElements(size);

			}
			return responseMessage;

		}
		
		public ResponseMessage<List<ThemesCategory>> getCategories() 
		{
			List<ThemesCategory> list=userDAO.getThemeCategories();
			
			ResponseMessage<List<ThemesCategory>> responseMessage=new ResponseMessage<>();
			
			int size=list.size();
			
			if(size==0)
			{
				
				responseMessage.setResult(null);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("No Categories present");
			}
			else
			{
				responseMessage.setResult(list);
				responseMessage.setStatus(HttpStatus.OK.value());
				responseMessage.setStatusText("All Themes Categories");
				responseMessage.setTotalElements(size);

			}
			return responseMessage;

		}
}
