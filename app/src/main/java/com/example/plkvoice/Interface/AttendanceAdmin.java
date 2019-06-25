package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AttendanceAdmin implements Serializable {

    @SerializedName("report")
    public String report;

    @SerializedName("phone")
    public String attendance;

}
