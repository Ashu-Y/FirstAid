package com.practice.android.firstaid.Models;

import java.util.List;

/**
 * Created by parven on 26-11-2017.
 */

public class DescModel {

    private String Title;
    private List<String> Desc;

    public DescModel() {
    }

    public DescModel(String Title, List<String> Desc) {
        this.Title = Title;
        this.Desc = Desc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public List<String> getDesc() {
        return Desc;
    }

    public void setDesc(List<String> Desc) {
        this.Desc = Desc;
    }
}
