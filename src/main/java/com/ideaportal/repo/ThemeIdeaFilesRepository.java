package com.ideaportal.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.ThemeIdeaFiles;



@Repository
public interface  ThemeIdeaFilesRepository extends  CrudRepository<ThemeIdeaFiles, Long> {
	
	

}