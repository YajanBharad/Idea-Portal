package com.ideaportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideaportal.models.ThemesCategory;

public interface ThemesCategoryRepository extends JpaRepository<ThemesCategory, Long> {
}