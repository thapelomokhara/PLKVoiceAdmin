package com.example.plkvoice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    EditText user_name,passwd,firstname,lastname,confirmPasswd;
    public static final String PREFS = "preferencesfFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        user_name = (EditText)findViewById(R.id.input_email);
        passwd = (EditText)findViewById(R.id.input_password);
        firstname = (EditText)findViewById(R.id.edName);
        lastname = (EditText)findViewById(R.id.edSurname);
        confirmPasswd = (EditText)findViewById(R.id.conPassword);


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Press ok to exit")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        setResult(RESULT_OK, new Intent().putExtra("EXIT", true));
                        RegistrationActivity.super.onBackPressed();
                        Intent nextIntent = new Intent(getApplicationContext(),Launcher.class);
                        startActivity(nextIntent);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }).create().show();
    }


    public void goToLogin(View view){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegistration(View view){


        final String str_UsernName = user_name.getText().toString().toUpperCase();
        String str_passwd= passwd.getText().toString().toUpperCase();
        String str_CofirmPasswd= confirmPasswd.getText().toString().toUpperCase();
        String str_firstName= firstname.getText().toString().toUpperCase();
        String str_lastName= lastname.getText().toString().toUpperCase();

        HashMap postData = new HashMap();

        postData.put("user_name", str_UsernName);
        postData.put("password", str_passwd);
        postData.put("name", str_firstName);
        postData.put("surname", str_lastName);

        if(str_passwd.equals(str_CofirmPasswd)){

            PostResponseAsyncTask task = new PostResponseAsyncTask(RegistrationActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    //   Toast.makeText(MainActivity.this, s , Toast.LENGTH_LONG).show();

                    if (s.contains("Successfully Registered")) {
                        //if the username and password match the corresponding contents, then the user
                        //navigates to the appropriate page

                        SharedPreferences preferences = getSharedPreferences(PREFS, 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", str_UsernName);
                        editor.commit();

                        Toast.makeText(RegistrationActivity.this, "Successful Registered", Toast.LENGTH_LONG).show();
                        user_name.setText("");
                        passwd.setText("");

                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } else if (s.contains("User Exists")) {
                        AlertDialog.Builder dialogBox = new AlertDialog.Builder(RegistrationActivity.this);
                        dialogBox.setMessage("Username already exists")
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

                    } else {
                        //if the username and/or password is invalid, a dialog box appears informs them of possible errors
                        AlertDialog.Builder dialogBox = new AlertDialog.Builder(RegistrationActivity.this);
                        dialogBox.setMessage("Incorrect Username or Password")
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
            task.execute("https://pnpapp.000webhostapp.com/registration.php");
        }else {
            AlertDialog.Builder dialogBox = new AlertDialog.Builder(RegistrationActivity.this);
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
        }
    }

    }

