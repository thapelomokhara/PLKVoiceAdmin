package com.example.plkvoice;

import com.example.plkvoice.Fragment.IssueFragment;

public class WarningMessage extends IssueFragment {

 /*   public static final boolean fieldsNotValid = adress.getText().toString().trim().equals("")
            && phone.getText().toString().trim().equals("") && issue.getText().toString().trim().equals("")
            && name.getText().toString().trim().equals("");//T
\
    public static final boolean fieldsNotValidOther = other.getText().toString().trim().equals("") && adress.getText().toString().trim().equals("")
            && phone.getText().toString().trim().equals("") && issue.getText().toString().trim().equals("")
            && name.getText().toString().trim().equals("");*/

    static boolean addressStatus = adress.getText().toString().trim().equals("");
    static boolean phoneNumberStatus = phone.getText().toString().trim().equals("");
    static boolean issueStatus = issue.getText().toString().trim().equals("");
    static boolean nameStatus = name.getText().toString().trim().equals("");

    public static final boolean fieldsNotValid = (addressStatus || phoneNumberStatus || issueStatus || nameStatus);
    // empty TRUE // one captured TRUE // all captured FALSE

    public static final boolean fieldsValid = (addressStatus && phoneNumberStatus && issueStatus && nameStatus);
    // empty TRUE // one captured FALSE // all captured FALSE
}


