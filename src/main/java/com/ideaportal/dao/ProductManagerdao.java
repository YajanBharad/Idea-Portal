package com.ideaportal.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
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
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DaoUtils utils;
	
	
	
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
	public List<Ideas> getMyIdeasList(long userID) 
	{
		return jdbcTemplate.execute("select idea_id from ideas where user_id=?", (PreparedStatementCallback<List<Ideas>>) ps -> {
			ps.setLong(1, userID);

			ResultSet resultSet=ps.executeQuery();

			return utils.buildList(resultSet);
		});
	}
	
}
