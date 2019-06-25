package com.example.plkvoice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plkvoice.Fragment.HomeFragment;
import com.example.plkvoice.Fragment.MessageOptionsFragment;
import com.example.plkvoice.Fragment.OptionsFragment;
import com.example.plkvoice.Fragment.ResetPasswordFragment;

public class HomeActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    public static final String PREFS = "preferencesfFile";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(PREFS, 0);
        final String customer = preferences.getString("username", null);

        DrawerLayout drawer =  findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUsername);
        username.setText("Welcome " + customer);
        ImageView profilePic = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgUser);
        profilePic.setImageResource(R.drawable.ic_profile_pic);

            navigationView = (NavigationView) findViewById(R.id.navigation_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.login).setVisible(false);


        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main1, homeFragment,
                homeFragment.getTag()).commit();
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
                        HomeActivity.super.onBackPressed();
                        Intent nextIntent = new Intent(getApplicationContext(),Launcher.class);
                        startActivity(nextIntent);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }).create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home)
        {
            com.example.plkvoice.Fragment.HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main1, homeFragment,
                    homeFragment.getTag()).commit();
        }

        else if (id == R.id.order)
        {
            OptionsFragment optionsFragment = new OptionsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main1, optionsFragment,
                    optionsFragment.getTag()).commit();
        }

        else if (id == R.id.pending)
        {
            MessageOptionsFragment messageFragment = new MessageOptionsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main1, messageFragment,
                    messageFragment.getTag()).commit();
        }

        else if (id == R.id.reset)
        {
            ResetPasswordFragment messageFragment = new ResetPasswordFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main1, messageFragment,
                    messageFragment.getTag()).commit();
        }

        else if (id == R.id.login)
        {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();;
        }


        else  if (id == R.id.logout)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Log out?")
                    .setMessage("Press ok to log out")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            setResult(RESULT_OK, new Intent().putExtra("EXIT", true));
                           HomeActivity.super.onBackPressed();
                            Intent nextIntent = new Intent(getApplicationContext(),Launcher.class);
                            startActivity(nextIntent);
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }).create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}