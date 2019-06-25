package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Attendance implements Serializable {

    @SerializedName("report")
    public String report;

    @SerializedName("attendance")
    public String attendance;

}
