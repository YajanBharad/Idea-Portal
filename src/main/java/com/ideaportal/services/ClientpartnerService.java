package com.ideaportal.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideaportal.dao.ClientPartnerDAO;
import com.ideaportal.models.ResponseMessage;
import com.ideaportal.models.ThemeIdeaFiles;
import com.ideaportal.models.Themes;



@Service
public class ClientpartnerService {
	
     ClientPartnerDAO clientPartnerDAO;

	public void saveArtifacts(List<ThemeIdeaFiles> artifactList, long themeID) {
		clientPartnerDAO.saveArtifacts(artifactList, themeID);
	}
	public ResponseMessage<Themes> saveTheme(Themes themes)
	{
		ResponseMessage<Themes> responseMessage=new ResponseMessage<>();
		responseMessage.setResult(clientPartnerDAO.saveTheme(themes));
		responseMessage.setStatus(HttpStatus.CREATED.value());
		responseMessage.setStatusText("theme_created");
		responseMessage.setTotalElements(1);
		return responseMessage;
	}

}
