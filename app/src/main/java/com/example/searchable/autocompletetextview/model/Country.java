package com.example.searchable.autocompletetextview.model;

/**
 * Created by Ravindu Fernando on 2019-12-12 at 09:43 AM
 */
public class Country {
    private String Name;
    private String Code;
//    private int flagImage;

    public Country(String name, String code) {
        this.Name = name;
        this.Code = code;
//        this.flagImage = flagImage;
    }

    public String getCountryName() {
        return Name;
    }

    public String getCode() {
        return Code;
    }

    //    public int getFlagImage() {
//        return flagImage;
//    }
}
