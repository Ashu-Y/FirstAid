package com.practice.android.firstaid.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ashutosh on 10/2/2017.
 */

public class UserInfo {

    private String Name;
    private String Gender;
    private String DOB;
    private String BloodGroup;
    private String PhoneNumber;
    private String Languages;
    private String InterestedinDonating;
    private String FirstLogin;
    private String ProfileUrl;
    private List<String> Cities;

    public UserInfo() {
    }

    public UserInfo(String Name,
                    String Gender,
                    String DOB,
                    String BloodGroup,
                    String PhoneNumber,
                    String Languages,
                    String InterestedinDonating,
                    String FirstLogin,
                    String ProfileUrl,
                    List Cities)
    {
        this.Name=Name;
        this.Gender=Gender;
        this.DOB=DOB;
        this.BloodGroup=BloodGroup;
        this.PhoneNumber=PhoneNumber;
        this.Languages=Languages;
        this.InterestedinDonating=InterestedinDonating;
        this.FirstLogin=FirstLogin;
        this.ProfileUrl = ProfileUrl;
        this.Cities=Cities;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public List<String> getCities() {
        return Cities;
    }

    public String getDOB() {
        return DOB;
    }

    public String getFirstLogin() {
        return FirstLogin;
    }

    public String getGender() {
        return Gender;
    }

    public String getInterestedinDonating() {
        return InterestedinDonating;
    }

    public String getLanguages() {
        return Languages;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }

    public void setProfileUrl(String ProfileUrl) {
        this.ProfileUrl = ProfileUrl;
    }

    @Exclude
    public Map<String,Object> toMap()
    {
        HashMap<String,Object> result=new HashMap<>();
        result.put("Name",Name);
        result.put("Gender",Gender);
        result.put("DOB",DOB);
        result.put("BloodGroup",BloodGroup);
        result.put("PhoneNumber", PhoneNumber);
        result.put("Languages",Languages);
        result.put("InterestedinDonating",InterestedinDonating);
        result.put("FirstLogin",FirstLogin);
        result.put("ProfileUrl", ProfileUrl);
        result.put("Cities",Cities);
        return result;
    }

}


