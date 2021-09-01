package com.ideaportal.controller;

import java.util.List;


import org.modelmapper.ModelMapper;
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

	  @GetMapping(value="/idea/{ideaID}")
	    public ResponseEntity<ResponseMessage<Ideas>> getIdeaByID(@PathVariable ("ideaID") long ideaID) throws Exception {
		  
		  ResponseMessage<Ideas> responseMessage=userService.getIdeaByIDResponseMessage(ideaID);

	        
		  return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
	        
	        
	  }
	  @GetMapping(value = "/themes/{themeID}/ideas/")
	    public ResponseEntity<ResponseMessage<List<Ideas>>> getIdeasByTheme(@PathVariable("themeID") long themeID) 
	    {

	        ResponseMessage<List<Ideas>> responseMessage = userService.getIdeasBythemeid(themeID);

	        

	        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
	    }
	
    
    @GetMapping(value="/themes")
    public ResponseEntity<ResponseMessage<List<Themes>>> getAllThemes()
    {

        ResponseMessage<List<Themes>> responseMessage = userService.getAllThemesResponseMessage();
        
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    
    //Function to get a theme by id
    @GetMapping(value = "/themes/{id}")
    public ResponseEntity<ResponseMessage<Themes>> getThemeByID(@PathVariable("id")String themeID) throws Exception {

	    Themes themes;
	    
	    themes = utils.findThemeByID(Long.parseLong(themeID));

        ResponseMessage<Themes> responseMessage = new ResponseMessage<>();

        if(themes==null) {
            throw new Exception("No themes present");
        }
	    else 
	    {
            responseMessage.setResult(themes);
            responseMessage.setStatus(HttpStatus.OK.value());
            responseMessage.setStatusText("Theme sent successfully");
            responseMessage.setTotalElements(1);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    @PutMapping(value="/user/idea/like")
    public ResponseEntity<ResponseMessage<Likes>> likeAnIdea(@RequestBody LikesDTO likesDTO)throws Exception {
            

        Likes likes= modelMapper.map(likesDTO, Likes.class);
    	String res=utils.isIdeaLiked(likes);
    	
    	Ideas idea=utils.isIdeaIDValid(likes.getIdea().getIdeaId());
    	
    	User user=utils.findByUserId(likes.getUser().getUserId());
      	if(user==null) {
            throw new UserNotFoundException("User Not Found, Please try again!");
        }
    	if(idea==null) {
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
        Comments comment = modelMapper.map(commentsDTO, Comments.class);
    	User user=utils.findByUserId(comment.getUser().getUserId());
    	Ideas idea=utils.isIdeaIDValid(comment.getIdea().getIdeaId());
    	if(user==null) {
            throw new UserNotFoundException("User Not Found, Please try again!");
        }
    	if(idea==null) {
            throw new Exception("Invalid Idea Id");
        }
        ResponseMessage<Comments> responseMessage = userService.commentAnIdeaResponseMessage(comment);

    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    	
    }
    //Function to get a  list of comments for an idea
    @GetMapping(value="idea/{ideaID}/comments")
    public ResponseEntity<ResponseMessage<List<Comments>>> getCommentsForIdea(@PathVariable ("ideaID") long ideaID) throws Exception
    {
        
    	Ideas idea=utils.isIdeaIDValid(ideaID);
    	if(idea==null) {
            throw new Exception("Invalid Idea ID");
        }

        ResponseMessage<List<Comments>> responseMessage = userService.getCommentForIdeaResponseMessage(ideaID);


    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    @GetMapping(value = "idea/{ideaID}/likes")
    public ResponseEntity<ResponseMessage<List<User>>> getLikesForIdea(@PathVariable ("ideaID") long ideaID) throws Exception
    {
        
    	Ideas idea=utils.isIdeaIDValid(ideaID);
    	if(idea==null) {
    	    
            throw new Exception("IDEA_NOT_FOUND");
        }
        

        ResponseMessage<List<User>> responseMessage = userService.getLikesForIdeaResponseMessage(ideaID);

       

    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    @GetMapping(value = "idea/{ideaID}/dislikes")
    public ResponseEntity<ResponseMessage<List<User>>> getDislikesForIdea(@PathVariable ("ideaID") long ideaID) throws Exception
    {
        
    	Ideas idea=utils.isIdeaIDValid(ideaID);
    	if(idea==null) {
            
            throw new Exception("Idea not found");
        }
       

        ResponseMessage<List<User>> responseMessage = userService.getDislikesForIdeaResponseMessage(ideaID);

        

    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
    
  //interest in idea
  	@PostMapping(path="/idea/interested")
  	public ResponseEntity<ResponseMessage<ParticipationResponse>> interestedParticipants(@RequestBody ParticipantDTO participantDTO) throws Exception
  	{
  		 ParticipationResponse participant = modelMapper.map(participantDTO, ParticipationResponse.class);
     	User user=utils.findByUserId(participant.getUser().getUserId());
     	Ideas idea=utils.isIdeaIDValid(participant.getIdea().getIdeaId());
     	if(user==null) {
             throw new UserNotFoundException("User Not Found, Please try again!");
         }
     	if(idea==null) {
             throw new Exception("Invalid Idea Id");
         }
     	ResponseMessage<ParticipationResponse> responseMessage = userService.enrollParticipant(participant);
     	
     	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
  	}
  	
  	@GetMapping(path="/idea/{ideaId}/interested")
  	public ResponseEntity<ResponseMessage<List<User>>> listOfInterestedParticipants(@PathVariable ("ideaId") long ideaId) throws Exception
  	{
  		Ideas idea=utils.isIdeaIDValid(ideaId);
    	if(idea==null) {
    	    
            throw new Exception("IDEA_NOT_FOUND");
        }
        

        ResponseMessage<List<User>> responseMessage = userService.getParticipantsForIdea(ideaId);

       

    	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
  	}
  	
   @PostMapping(path="user/theme/themecategory")
   public ResponseEntity<ResponseMessage<ThemesCategory>> createThemeCategory(@RequestBody ThemesCategory category) throws Exception
   {
	   int id=utils.isCategoryPresent(category.getThemeCategoryName());
	   if(id==1)
		   throw new Exception("Category Already Present");
	   ResponseMessage<ThemesCategory> responseMessage=userDAO.addCategory(category);
	   return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
   
   @GetMapping(path="theme/all/themecategory")
   public ResponseEntity<ResponseMessage<List<ThemesCategory>>> showThemeCategory()
   {
	   ResponseMessage<List<ThemesCategory>> responseMessage = userService.getCategories();

   	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
   
   @GetMapping(value="/themes/{themeID}/ideas/mostlikes")
   public ResponseEntity<ResponseMessage<List<Ideas>>> getIdeasByMostLikesForTheme(@PathVariable("themeID") long themeID) throws Exception
   {
       

   	Themes theme=utils.findThemeByID(themeID);
   	
   	if(theme==null) {
   	    
           throw new Exception("THEME_NOT_FOUND");
       }

   	ResponseMessage<List<Ideas>> responseMessage = userService.getIdeasByMostLikesResponseMessage(themeID);

      

   	return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
   @GetMapping(value="/themes/{themeID}/ideas/mostcomments")
   public ResponseEntity<ResponseMessage<List<Ideas>>> getIdeasByMostCommentsForTheme(@PathVariable ("themeID") long themeID) throws Exception
   {
     

       Themes theme=utils.findThemeByID(themeID);

       if(theme==null) {
         
           throw new Exception("THEME_NOT_FOUND");
       }

       ResponseMessage<List<Ideas>> responseMessage = userService.getIdeasByMostCommentsResponseMessage(themeID);

      
       return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
   }
  
	   
	   
	   
	   
	   
	   
	   
	   
   
   
   
   
}
