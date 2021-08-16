package com.ideaportal.repo;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideaportal.models.Themes;


@Repository
public interface ThemesRepository extends  CrudRepository<Themes, Long> {

}