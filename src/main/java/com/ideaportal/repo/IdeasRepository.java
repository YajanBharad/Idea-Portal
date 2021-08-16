package com.ideaportal.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Ideas;


@Repository
public interface IdeasRepository extends  CrudRepository<Ideas, Long> {

}