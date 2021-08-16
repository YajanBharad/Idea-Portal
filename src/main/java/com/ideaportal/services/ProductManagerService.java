package com.ideaportal.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideaportal.dao.ProductManagerdao;
import com.ideaportal.models.Ideas;
import com.ideaportal.models.ResponseMessage;
import com.ideaportal.models.ThemeIdeaFiles;

@Service
public class ProductManagerService {

	@Autowired
	ProductManagerdao pmDAO;
	

	
	public ResponseMessage<Ideas> createNewIdeaResponseMessage(final Ideas ideas) throws IOException, URISyntaxException {
         ResponseMessage<Ideas> responseMessage=new ResponseMessage<>();
		responseMessage.setResult(pmDAO.createNewIdeaObject(ideas));
		responseMessage.setStatus(HttpStatus.CREATED.value());
		
		

		return responseMessage;
	}
	 public void saveArtifacts(final List<ThemeIdeaFiles> artifactList, final long ideaID) {
	        this.pmDAO.saveArtifacts(artifactList, ideaID);
	    }
		
}
