package com.practice.android.firstaid.Models;

/**
 * Created by parven on 16-10-2017.
 */

public class FaSubCategory {

    private String IconUrl;
    private String Name;

    public FaSubCategory() {
    }

    public FaSubCategory(String IconUrl, String Name) {
        this.IconUrl = IconUrl;
        this.Name = Name;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public void setIconUrl(String IconUrl) {
        this.IconUrl = IconUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
