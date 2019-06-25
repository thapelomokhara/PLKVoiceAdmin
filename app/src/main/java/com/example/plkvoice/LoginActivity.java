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

public class LoginActivity extends AppCompatActivity {
    EditText user_name,passwd;
    public static final String PREFS = "preferencesfFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user_name = (EditText)findViewById(R.id.txtUsername);
        passwd = (EditText)findViewById(R.id.txtPassword);


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
                        LoginActivity.super.onBackPressed();
                        Intent nextIntent = new Intent(getApplicationContext(),Launcher.class);
                        startActivity(nextIntent);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }).create().show();
    }


    public void goToRegistration(View view){
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void onLogin(View view){


        final String str_UsernName = user_name.getText().toString().toUpperCase();
        String str_passwd= passwd.getText().toString().toUpperCase();
       // Toast.makeText(MainActivity.this,str_passwd +"Successful login" + str_passwd, Toast.LENGTH_LONG).show();


        HashMap postData = new HashMap();

        postData.put("user_name", str_UsernName);
        postData.put("password", str_passwd);

        PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

             //   Toast.makeText(MainActivity.this, s , Toast.LENGTH_LONG).show();

                if (s.contains("Successfully Logged-in")) {
                    //if the username and password match the corresponding contents, then the user
                    //navigates to the appropriate page

                    SharedPreferences preferences = getSharedPreferences(PREFS, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", str_UsernName);
                    editor.commit();

                    Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                    user_name.setText("");
                    passwd.setText("");

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    //if the username and/or password is invalid, a dialog box appears informs them of possible errors
                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(LoginActivity.this);
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
        task.execute("https://pnpapp.000webhostapp.com/login.php");
    }



    }

