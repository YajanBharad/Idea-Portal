package com.ideaportal.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Ideas;
import com.ideaportal.models.ThemeIdeaFiles;
import com.ideaportal.repo.IdeasRepository;
import com.ideaportal.repo.ThemeIdeaFilesRepository;

@Repository
public class ProductManagerdao {
	
	@Autowired
	IdeasRepository ideasRepository;
	@Autowired
	ThemeIdeaFilesRepository themeIdeaFilesRepository;
	
	
	
	public Ideas createNewIdeaObject(Ideas ideas)
	{

		return ideasRepository.save(ideas);
	}
	public void saveArtifacts(List<ThemeIdeaFiles> thflist, long ideaID) {
		Ideas ideas = ideasRepository.findById(ideaID).orElse(null);
		if (ideas != null) {
			ideas.setIdeaFiles((List<ThemeIdeaFiles>) themeIdeaFilesRepository.saveAll(thflist));
		}
	}
	
}
