package com.example.plkvoice.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plkvoice.R;
import com.example.plkvoice.RegistrationActivity;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.HashMap;


public class ResetPasswordFragment extends Fragment {
    View view;
    final String LOG = "SendMessage.this";
    public static final String PREFS = "preferencesfFile";
    String type;
    Serializable serializable;
    EditText passwd,newPasswd,conPasswd;
    static EditText message, num;
    Button button;


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.reset, container, false);
        message = view.findViewById(R.id.Message);
        num = view.findViewById(R.id.Num);
        button = view.findViewById(R.id.btn_reset);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);


        newPasswd = view.findViewById(R.id.newPasswd);
        passwd = view.findViewById(R.id.passwd);
        conPasswd = view.findViewById(R.id.conNewPasswd);
        toolbar.setTitle("Reset Password");
        //UPDATE `user` SET `password`="321" WHERE email = "EMMANUEL" AND password = "123"
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = ResetPasswordFragment.this.getActivity().getSharedPreferences(PREFS, 0);
                if(!newPasswd.getText().toString().toUpperCase().equals(conPasswd.getText().toString().toUpperCase())){
                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(ResetPasswordFragment.this.getActivity());
                    dialogBox.setMessage("Password not matching")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();//closes the dialog box
                                }
                            });

                    AlertDialog dialog = dialogBox.create();
                    dialog.setTitle("ERROR!");
                    dialog.show();
                    dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#808080"));
                }else{

                    if (!newPasswd.getText().toString().trim().equals("")) {
                        if(!passwd.getText().toString().trim().equals("")){
                            if(!conPasswd.getText().toString().trim().equals("")) {

                                HashMap postData = new HashMap();
                                postData.put("newPassword", newPasswd.getText().toString().toUpperCase());
                                postData.put("password", passwd.getText().toString().toUpperCase());
                                postData.put("userName", preferences.getString("username", null));

                                PostResponseAsyncTask cardTask = new PostResponseAsyncTask(ResetPasswordFragment.this.getActivity(), postData, new AsyncResponse() {
                                    @Override
                                    public void processFinish(String s) {
                                        Log.d(LOG, s);
                                        if (s.contains("success")) {
                                            Toast toast = Toast.makeText(ResetPasswordFragment.this.getActivity(), "Password was successfully changed", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                            toast.show();
                                            HomeFragment deliveryFragment = new HomeFragment();
                                            FragmentManager manager = getFragmentManager();
                                            manager.beginTransaction().replace(R.id.content_main1, deliveryFragment,
                                                    deliveryFragment.getTag()).commit();
                                        }else if(s.contains("incorrect current password")){
                                            AlertDialog.Builder dialogBox = new AlertDialog.Builder(ResetPasswordFragment.this.getActivity());
                                            dialogBox.setMessage("Your current password is not correct")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();//closes the dialog box
                                                        }
                                                    });

                                            AlertDialog dialog = dialogBox.create();
                                            dialog.setTitle("ERROR!");
                                            dialog.show();
                                            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#808080"));
                                        }
                                    }
                                });

                                cardTask.execute("https://pnpapp.000webhostapp.com/reset.php");
                            } else {
                                Toast.makeText(ResetPasswordFragment.this.getActivity(),"Please capture all the passwords",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ResetPasswordFragment.this.getActivity(),"Please capture all the passwords",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(ResetPasswordFragment.this.getActivity(),"Please capture all the passwords",Toast.LENGTH_SHORT).show();

                       /* if(num.getText().toString().isEmpty()){
                            Toast.makeText(ResetPasswordFragment.this.getActivity(), "Please capture all the fields.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordFragment.this.getActivity(), "Please capture your update.", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }

            }
        });

        return view;
    }
}
