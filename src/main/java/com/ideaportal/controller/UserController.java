package com.ideaportal.controller;

import java.util.List;



import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideaportal.dao.DaoUtils;
import com.ideaportal.dao.UserDAO;
import com.ideaportal.dto.CommentsDTO;
import com.ideaportal.dto.LikesDTO;
import com.ideaportal.dto.ParticipantDTO;
import com.ideaportal.dto.UserDTO;
import com.ideaportal.exception.UserAuthException;
import com.ideaportal.exception.UserNotFoundException;
import com.ideaportal.models.Comments;
import com.ideaportal.models.Ideas;
import com.ideaportal.models.Likes;
import com.ideaportal.models.Login;
import com.ideaportal.models.ParticipationResponse;
import com.ideaportal.models.ResponseMessage;
import com.ideaportal.models.Themes;
import com.ideaportal.models.ThemesCategory;
import com.ideaportal.models.User;
import com.ideaportal.repo.ParticipationRepository;
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
	 @Autowired   
	 ParticipationRepository partRepo;
	 
	 private static final Logger LOG = LoggerFactory.getLogger(UserController.class); 
	
	@PostMapping(value = "/signup")
	public ResponseEntity<ResponseMessage<User>> createNewUser(@RequestBody UserDTO userDTO)
	{
		LOG.info("Request URL: POST Signup");
         User userDetails = modelMapper.map(userDTO, User.class);
         System.out.println(userDetails);
        ResponseMessage<User> responseMessage = userService.addUser(userDetails);
        LOG.info("User Signup successfully");
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
	
	@PostMapping(value = "/login")
	public ResponseEntity<ResponseMessage<User>> loginUser(@RequestBody Login userDetails) throws UserAuthException 
	{
		LOG.info("Request URL: POST Login");
        ResponseMessage<User> responseMessage = userService.checkCredentials(userDetails);
        LOG.info("User Logged in successfully");
		return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));

    }

	  @GetMapping(value="/idea/{ideaID}")
	    public ResponseEntity<ResponseMessage<Ideas>> getIdeaByID(@PathVariable ("ideaID") long ideaID) throws Exception {  
		  LOG.info("Request URL: GET Idea by id");
		  ResponseMessage<Ideas> responseMessage=userService.getIdeaByIDResponseMessage(ideaID);
	      LOG.info("Returned Idea by it's id");  
		  return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
	        
	        
	  }
	  @GetMapping(value = "/themes/{themeID}/ideas/")
	    public ResponseEntity<ResponseMessage<List<Ideas>>> getIdeasByTheme(@PathVariable("themeID") long themeID) 
	    {
		    LOG.info("Request URL: GET All Ideas of a Theme");
	        ResponseMessage<List<Ideas>> responseMessage = userService.getIdeasBythemeid(themeID); 
	        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
	    }
	
    
    @GetMapping(value="/themes")
    public ResponseEntity<ResponseMessage<List<Themes>>> getAllThemes()
    {
    	 LOG.info("Request URL: GET All Themes");
        ResponseMessage<List<Themes>> responseMessage = userService.getAllThemesResponseMessage();
        
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    
    //Function to get a theme by id
    @GetMapping(value = "/themes/{id}")
    public ResponseEntity<ResponseMessage<Themes>> getThemeByID(@PathVariable("id")String themeID) throws Exception {
    	LOG.info("Request URL: GET Theme by ID");
	    Themes themes;
	    
	    themes = utils.findThemeByID(Long.parseLong(themeID));

        ResponseMessage<Themes> responseMessage = new ResponseMessage<>();

        if(themes==null) {
        	LOG.error("NO themes Present");
            throw new Exception("No themes present");
        }
	    else 
	    {
	    	LOG.info("Particular Theme returned");
            responseMessage.setResult(themes);
            responseMessage.setStatus(HttpStatus.OK.value());
            responseMessage.setStatusText("Theme sent successfully");
            responseMessage.setTotalElements(1);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    @PutMapping(value="/user/idea/like")
    public ResponseEntity<ResponseMessage<Likes>> likeAnIdea(@RequestBody LikesDTO likesDTO)throws Exception {
    	LOG.info("Request URL: PUT a like on an idea");
        Likes likes= modelMapper.map(likesDTO, Likes.class);
    	String res=utils.isIdeaLiked(likes);
    	
    	Ideas idea=utils.isIdeaIDValid(likes.getIdea().getIdeaId());
    	
    	User user=utils.findByUserId(likes.getUser().getUserId());
      	if(user==null) {
      		LOG.error("User Not present");
            throw new UserNotFoundException("User Not Found, Please try again!");
        }
    	if(idea==null) {
    		LOG.error("No idea present with this id");
            throw new Exception("Invalid Idea Id");
    	}

        ResponseMessage<Likes> responseMessage = userService.likeAnIdeaResponseMessage(likes);      

    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    
    //Function to support that user can comment an idea
    @PostMapping(value="/user/idea/comment")
    public ResponseEntity<ResponseMessage<Comments>> commentAnIdea(@RequestBody CommentsDTO commentsDTO) throws
            Exception
    {
    	LOG.info("Request URL: POST a comment on an idea");
        Comments comment = modelMapper.map(commentsDTO, Comments.class);
    	User user=utils.findByUserId(comment.getUser().getUserId());
    	Ideas idea=utils.isIdeaIDValid(comment.getIdea().getIdeaId());
    	if(user==null) {
    		LOG.error("User Not present");
            throw new UserNotFoundException("User Not Found, Please try again!");
        }
    	if(idea==null) {
    		LOG.error("No idea present with this id");
            throw new Exception("Invalid Idea Id");
        }
        ResponseMessage<Comments> responseMessage = userService.commentAnIdeaResponseMessage(comment);

    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    	
    }
    //Function to get a  list of comments for an idea
    @GetMapping(value="idea/{ideaID}/comments")
    public ResponseEntity<ResponseMessage<List<Comments>>> getCommentsForIdea(@PathVariable ("ideaID") long ideaID) throws Exception
    {
    	LOG.info("Request URL: GET all comment on an idea");
    	Ideas idea=utils.isIdeaIDValid(ideaID);
    	if(idea==null) {
    		LOG.error("No idea present with this id");
            throw new Exception("Invalid Idea ID");
        }

        ResponseMessage<List<Comments>> responseMessage = userService.getCommentForIdeaResponseMessage(ideaID);


    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    @GetMapping(value = "idea/{ideaID}/likes")
    public ResponseEntity<ResponseMessage<List<User>>> getLikesForIdea(@PathVariable ("ideaID") long ideaID) throws Exception
    {
    	LOG.info("Request URL: GET all likes on an idea");
    	Ideas idea=utils.isIdeaIDValid(ideaID);
    	if(idea==null) {
    		LOG.error("No idea present with this id");
            throw new Exception("IDEA_NOT_FOUND");
        }
        

        ResponseMessage<List<User>> responseMessage = userService.getLikesForIdeaResponseMessage(ideaID);

       

    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    @GetMapping(value = "idea/{ideaID}/dislikes")
    public ResponseEntity<ResponseMessage<List<User>>> getDislikesForIdea(@PathVariable ("ideaID") long ideaID) throws Exception
    {
    	LOG.info("Request URL: GET all dislikes on an idea");
    	Ideas idea=utils.isIdeaIDValid(ideaID);
    	if(idea==null) {
    		LOG.error("No idea present with this id");
            throw new Exception("Idea not found");
        }
        ResponseMessage<List<User>> responseMessage = userService.getDislikesForIdeaResponseMessage(ideaID);
    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    
  //interest in idea
  	@PostMapping(path="/idea/interested")
  	public ResponseEntity<ResponseMessage<ParticipationResponse>> interestedParticipants(@RequestBody ParticipantDTO participantDTO) throws Exception
  	{
  		LOG.info("Request URL: POST your participation interest on an idea");
  		 ParticipationResponse participant = modelMapper.map(participantDTO, ParticipationResponse.class);
     	User user=utils.findByUserId(participant.getUser().getUserId());
     	Ideas idea=utils.isIdeaIDValid(participant.getIdea().getIdeaId());
     	if(user==null) {
     		LOG.error("No User present with this id");
             throw new UserNotFoundException("User Not Found, Please try again!");
         }
     	if(idea==null) {
     		LOG.error("No idea present with this id");
             throw new Exception("Invalid Idea Id");
         }
     	ResponseMessage<ParticipationResponse> responseMessage = userService.enrollParticipant(participant);
     	
     	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
  	}
  	
  	@GetMapping(path="/idea/{ideaId}/interested")
  	public ResponseEntity<ResponseMessage<List<User>>> listOfInterestedParticipants(@PathVariable ("ideaId") long ideaId) throws Exception
  	{
  		LOG.info("Request URL: GET all participants interest on an idea");
  		Ideas idea=utils.isIdeaIDValid(ideaId);
    	if(idea==null) {
    		LOG.error("No idea present with this id");
            throw new Exception("IDEA_NOT_FOUND");
        }
        ResponseMessage<List<User>> responseMessage = userService.getParticipantsForIdea(ideaId);
    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
  	}
  	
   @PostMapping(path="user/theme/themecategory")
   public ResponseEntity<ResponseMessage<ThemesCategory>> createThemeCategory(@RequestBody ThemesCategory category) throws Exception
   {
	   LOG.info("Request URL: POST create new Theme Category");
	   int id=utils.isCategoryPresent(category.getThemeCategoryName());
	   if(id==1)
		   throw new Exception("Category Already Present");
	   ResponseMessage<ThemesCategory> responseMessage=userDAO.addCategory(category);
	   return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
   
   @GetMapping(path="theme/all/themecategory")
   public ResponseEntity<ResponseMessage<List<ThemesCategory>>> showThemeCategory()
   {
	   LOG.info("Request URL: GET all Theme Category");
	   ResponseMessage<List<ThemesCategory>> responseMessage = userService.getCategories();

   	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
   
   @GetMapping(value="/themes/{themeID}/ideas/mostlikes")
   public ResponseEntity<ResponseMessage<List<Ideas>>> getIdeasByMostLikesForTheme(@PathVariable("themeID") long themeID) throws Exception
   {
       
	   LOG.info("Request URL: GET all Ideas according to likes");
   	Themes theme=utils.findThemeByID(themeID);
   	
   	if(theme==null) {
   	    LOG.error("No such theme present");
           throw new Exception("THEME_NOT_FOUND");
       }
   	ResponseMessage<List<Ideas>> responseMessage = userService.getIdeasByMostLikesResponseMessage(themeID);
   	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
   @GetMapping(value="/themes/{themeID}/ideas/mostcomments")
   public ResponseEntity<ResponseMessage<List<Ideas>>> getIdeasByMostCommentsForTheme(@PathVariable ("themeID") long themeID) throws Exception
   {   
	   LOG.info("Request URL: GET all Ideas according to comment");
       Themes theme=utils.findThemeByID(themeID);
       if(theme==null) {
    	   LOG.error("No such theme present");
           throw new Exception("THEME_NOT_FOUND");
       }
       ResponseMessage<List<Ideas>> responseMessage = userService.getIdeasByMostCommentsResponseMessage(themeID);
       return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
   
 //Function to update user password
   @PutMapping(value ="/user/profile/update/password")
   public ResponseEntity<ResponseMessage<User>> updateUserPassword(@RequestBody UserDTO userDTO)
   {
	   LOG.info("Request URL: PUT update Password");
       User userDetail = modelMapper.map(userDTO, User.class);
   	   ResponseMessage<User> responseMessage=userService.saveUserPasswordResponseMessage(userDetail);
   	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
  

}
