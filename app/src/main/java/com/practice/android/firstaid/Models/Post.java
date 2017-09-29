package com.practice.android.firstaid.Models;

/**
 * Created by Ashutosh on 9/24/2017.
 */

public class Post {

    public String name, status, date, time;

    public Post(String name, String status, String date, String time) {
        this.name = name;
        this.status = status;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
