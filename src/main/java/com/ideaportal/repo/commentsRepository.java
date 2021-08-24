package com.ideaportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Comments;

@Repository
public interface  commentsRepository  extends JpaRepository<Comments, Long>{
	
}