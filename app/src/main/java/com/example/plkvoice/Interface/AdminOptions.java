package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AdminOptions implements Serializable {

    @SerializedName("phone")
    public String phone;

    @SerializedName("name")
    public String name;

    @SerializedName("address")
    public String address;

    @SerializedName("other")
    public String other;

    @SerializedName("issue")
    public String issue;

    @SerializedName("img")
    public String img;

}
