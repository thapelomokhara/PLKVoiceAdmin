package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class updates implements Serializable {

    @SerializedName("updates")
    public String updates;

    @SerializedName("summary")
    public String summary;
}
