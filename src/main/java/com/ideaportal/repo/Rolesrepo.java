package com.ideaportal.repo;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Roles;


@Repository
public interface Rolesrepo extends  CrudRepository<Roles, Integer> {
	

}