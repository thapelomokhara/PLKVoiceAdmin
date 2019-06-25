package com.example.plkvoice.Fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.plkvoice.Interface.Options;
import com.example.plkvoice.R;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.HashMap;


public class IssueFragment extends Fragment {
    View view;
    final String LOG = "IssueFragment.this";
    String type;
    Serializable serializable;
    public static EditText other, name, adress, phone, issue;
    public static TextView textView;
    Button button;


    public IssueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issue, container, false);
        other = view.findViewById(R.id.Other);
        textView = view.findViewById(R.id.CvAOther);
        name = view.findViewById(R.id.Name);
        adress = view.findViewById(R.id.Adress);
        phone = view.findViewById(R.id.Num);
        issue = view.findViewById(R.id.Issue);
        button = view.findViewById(R.id.btnSubmit);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        serializable = getArguments().getSerializable("options");
        Options options = (Options) getArguments().getSerializable("options");
        type = options.options_code;
        if (!type.equals("More")) {
            other.setVisibility(view.GONE);
            textView.setVisibility(view.GONE);
        }


        toolbar.setTitle(type + " Issue");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!type.equals("More")) {
                    HashMap postData = new HashMap();
                    postData.put("other", "none");
                    postData.put("address", adress.getText().toString());
                    postData.put("phone", phone.getText().toString());
                    postData.put("issue", issue.getText().toString());
                    postData.put("name", name.getText().toString());
                    postData.put("type", type);

                    if (!adress.getText().toString().trim().equals("")) {
                        if (!name.getText().toString().trim().equals("")) {
                            if (!phone.getText().toString().trim().equals("")) {
                                if (phone.getText().toString().length() == 10) {
                                    if (!issue.getText().toString().trim().equals("")) {
                                        PostResponseAsyncTask cardTask = new PostResponseAsyncTask(IssueFragment.this.getActivity(), postData, new AsyncResponse() {
                                            @Override
                                            public void processFinish(String s) {
                                                Log.d(LOG, s);
                                                if (s.contains("success")) {
                                                    Toast toast = Toast.makeText(IssueFragment.this.getActivity(), type + " Issue Successfully Sent.", Toast.LENGTH_SHORT);
                                                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                                    toast.show();
                                                    HomeFragment deliveryFragment = new HomeFragment();
                                                    FragmentManager manager = getFragmentManager();
                                                    manager.beginTransaction().replace(R.id.content_main1, deliveryFragment,
                                                            deliveryFragment.getTag()).commit();
                                                }

                                            }
                                        });
                                        cardTask.execute("https://pnpapp.000webhostapp.com/issue.php");
                                    } else
                                        Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(IssueFragment.this.getActivity(), "Your Phone Number must have 10 digits.", Toast.LENGTH_SHORT).show();
                            }   else
                                Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();

                } else {

                    HashMap postData = new HashMap();
                    postData.put("other", other.getText().toString());
                    postData.put("address", adress.getText().toString());
                    postData.put("phone", phone.getText().toString());
                    postData.put("issue", issue.getText().toString());
                    postData.put("name", name.getText().toString());
                    postData.put("type", type);

                    if (!adress.getText().toString().trim().equals("")) {
                        if (!name.getText().toString().trim().equals("")) {
                            if (!phone.getText().toString().trim().equals("")) {
                                if (phone.getText().toString().length() == 10) {
                                    if (!issue.getText().toString().trim().equals("")) {
                                        if (!other.getText().toString().trim().equals("")) {
                                            PostResponseAsyncTask cardTask = new PostResponseAsyncTask(IssueFragment.this.getActivity(), postData, new AsyncResponse() {
                                                @Override
                                                public void processFinish(String s) {
                                                    Log.d(LOG, s);
                                                    if (s.contains("success")) {
                                                        Toast toast = Toast.makeText(IssueFragment.this.getActivity(), type + " Issue Successfully Sent", Toast.LENGTH_SHORT);
                                                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                                        toast.show();
                                                        HomeFragment deliveryFragment = new HomeFragment();
                                                        FragmentManager manager = getFragmentManager();
                                                        manager.beginTransaction().replace(R.id.content_main1, deliveryFragment,
                                                                deliveryFragment.getTag()).commit();
                                                    }

                                                }
                                            });
                                            cardTask.execute("https://pnpapp.000webhostapp.com/issue.php");
                                        } else
                                            Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(IssueFragment.this.getActivity(), "Your Phone Number must have 10 digits.", Toast.LENGTH_SHORT).show();
                        }else
                                Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(IssueFragment.this.getActivity(), "Please capture all the fields above ", Toast.LENGTH_SHORT).show();
                }
            }

        });


        Button back =  view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailFragment = new OptionsFragment();
                Bundle bundle = new Bundle();
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();

            }

        });

        return view;
    }

    public void onBackPressed() {
        Fragment detailFragment = new OptionsFragment();
        Bundle bundle = new Bundle();
        detailFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                .addToBackStack("HomeDetail").commit();
    }

}
