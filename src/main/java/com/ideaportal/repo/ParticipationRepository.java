package com.ideaportal.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.ParticipationResponse;

@Repository
public interface  ParticipationRepository extends  CrudRepository<ParticipationResponse, Long> {

}