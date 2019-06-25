package com.example.plkvoice.Interface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ReceivedMessage implements Serializable {

    @SerializedName("Message")
    public String message;

    @SerializedName("Message_summary")
    public String message_summary;
}
