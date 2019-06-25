package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AttandanceReporting implements Serializable {

    @SerializedName("AttandanceReporting")
    public String attandanceReporting;

    @SerializedName("IMG_URL")
    public String img_url;

}
