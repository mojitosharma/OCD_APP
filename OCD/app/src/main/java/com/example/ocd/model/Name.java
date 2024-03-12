package com.example.ocd.model;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Name implements Serializable {
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;

    public Name(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Name(String name) {
        int lastSpaceIndex = name.lastIndexOf(" ");
        if (lastSpaceIndex != -1) {
            this.first_name = name.substring(0, lastSpaceIndex);
            this.last_name = name.substring(lastSpaceIndex + 1);
        } else {
            this.first_name = name;
        }
    }

    @NonNull
    @Override
    public String toString() {
        if(last_name != null){
            return first_name + " " + last_name;
        } else {
            return first_name;
        }
    }
}

