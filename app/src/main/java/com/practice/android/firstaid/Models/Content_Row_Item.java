package com.practice.android.firstaid.Models;

/**
 * Created by parven on 22-11-2017.
 */

public class Content_Row_Item {

    private int Image;
    private String HText;
    private String SText1;
    private String SText2;
    private String SText3;

    public Content_Row_Item(int Image, String HText, String SText1, String SText2, String SText3) {
        this.Image = Image;
        this.HText = HText;
        this.SText1 = SText1;
        this.SText2 = SText2;
        this.SText3 = SText3;

    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getHText() {
        return HText;
    }

    public void setHText(String HText) {
        this.HText = HText;
    }

    public String getSText1() {
        return SText1;
    }

    public void setSText1(String SText1) {
        this.SText1 = SText1;
    }

    public String getSText2() {
        return SText2;
    }

    public void setSText2(String SText2) {
        this.SText2 = SText2;
    }

    public String getSText3() {
        return SText3;
    }

    public void setSText3(String SText3) {
        this.SText3 = SText3;
    }
}