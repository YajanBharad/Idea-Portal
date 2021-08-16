package com.ideaportal.repo;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Comments;

@Repository
public interface  commentsRepository  extends CrudRepository<Comments, Long>{

}