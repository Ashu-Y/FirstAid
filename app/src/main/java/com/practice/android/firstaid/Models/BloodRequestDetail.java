package com.practice.android.firstaid.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by parven on 06-10-2017.
 */

public class BloodRequestDetail {

    private String Name;
    private String Phone;
    private String City;
    private String Comments;
    private String BloodGroup;
    private String Date;
    private String Time;
    private String Status;

    public BloodRequestDetail() {
    }

    public BloodRequestDetail(String Name, String Phone, String City, String Comments, String BloodGroup,
                              String Date, String Time, String Status) {
        this.Name = Name;
        this.Phone = Phone;
        this.City = City;
        this.Comments = Comments;
        this.BloodGroup = BloodGroup;
        this.Date = Date;
        this.Time = Time;
        this.Status = Status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String BloodGroup) {
        this.BloodGroup = BloodGroup;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public Map<String,Object> toMap()
    {
        HashMap<String,Object> result=new HashMap<>();
        result.put("Name",Name);
        result.put("BloodGroup",BloodGroup);
        result.put("Phone", Phone);
        result.put("City",City);
        result.put("Date", Date);
        result.put("Time", Time);
        result.put("Status", Status);
        return result;
    }
}
