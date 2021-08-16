package com.ideaportal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class ThemesCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_category_id")
    private long themeCategoryID;

    @Column(nullable = false, name = "theme_category_name")
    private String themeCategoryName;

    public ThemesCategory() {
    }

    public ThemesCategory(int themeCategoryID, String themeCategoryName) {
        this.themeCategoryID = themeCategoryID;
        this.themeCategoryName = themeCategoryName;
    }

    public long getThemeCategoryID() {
        return themeCategoryID;
    }

    public void setThemeCategoryID(long themeCategoryID) {
        this.themeCategoryID = themeCategoryID;
    }

    public String getThemeCategoryName() {
        return themeCategoryName;
    }

    public void setThemeCategoryName(String themeCategoryName) {
        this.themeCategoryName = themeCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemesCategory that = (ThemesCategory) o;
        return themeCategoryID == that.themeCategoryID && themeCategoryName.equals(that.themeCategoryName);
    }

    
}