package com.practice.android.firstaid.Activities;

/**
 * Created by Ashutosh on 10/2/2017.
 */

public class Matter {

    private String title, sub;

    public Matter() {
    }

    public Matter(String title, String sub) {
        this.title = title;
        this.sub = sub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
