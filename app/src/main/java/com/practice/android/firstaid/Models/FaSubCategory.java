package com.practice.android.firstaid.Models;

/**
 * Created by parven on 16-10-2017.
 */

public class FaSubCategory {

    private int img;
    private String subCategoryName;

    public FaSubCategory(int img, String subCategoryName) {
        this.img = img;
        this.subCategoryName = subCategoryName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
