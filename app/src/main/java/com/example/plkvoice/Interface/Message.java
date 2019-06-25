package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Message implements Serializable {

    @SerializedName("Message_code")
    public String message_code;

    @SerializedName("IMG_URL")
    public String img_url;

}
