package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Options implements Serializable {

    @SerializedName("option_code")
    public String options_code;

    @SerializedName("IMG_URL")
    public String img_url;

}
