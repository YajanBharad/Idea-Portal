package com.ideaportal.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Likes;

@Repository

public interface LikeRepository  extends CrudRepository<Likes, Long>{

}