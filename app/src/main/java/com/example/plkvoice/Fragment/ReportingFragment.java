package com.example.plkvoice.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plkvoice.R;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.HashMap;


public class ReportingFragment extends Fragment {
    View view;
    final String LOG = "SendMessage.this";
    String type;
    Serializable serializable;
    static EditText report, num;
    Button button,prevButton;


    public ReportingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_report, container, false);
        report = view.findViewById(R.id.Report);
        num = view.findViewById(R.id.Num);
        button = view.findViewById(R.id.btnSubmit);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);

        toolbar.setTitle("Reporting");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!report.getText().toString().trim().equals("")) {

                    if(!num.getText().toString().trim().equals("")){
                        if(num.getText().toString().length() == 10) {

                            HashMap postData = new HashMap();
                            postData.put("report", report.getText().toString());
                            postData.put("number", num.getText().toString());
                            postData.put("attendance", "No");

                            PostResponseAsyncTask cardTask = new PostResponseAsyncTask(ReportingFragment.this.getActivity(), postData, new AsyncResponse() {
                                @Override
                                public void processFinish(String s) {
                                    Log.d(LOG, s);
                                    if (s.contains("success")) {

                                        Toast.makeText(ReportingFragment.this.getActivity(), "Report Sent Successfully.", Toast.LENGTH_SHORT).show();
                                        HomeFragment deliveryFragment = new HomeFragment();
                                        FragmentManager manager = getFragmentManager();
                                        manager.beginTransaction().replace(R.id.content_main1, deliveryFragment,
                                                deliveryFragment.getTag()).commit();
                                    }
                                }
                            });

                            cardTask.execute("https://pnpapp.000webhostapp.com/report.php");
                }
                        else {
                            Toast.makeText(ReportingFragment.this.getActivity(),"Your Phone Number must have 10 digits.",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ReportingFragment.this.getActivity(),"Please capture your Phone Number.",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(num.getText().toString().isEmpty()){
                        Toast.makeText(ReportingFragment.this.getActivity(), "Please capture all the Fields.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ReportingFragment.this.getActivity(), "Please capture a Report.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button back =  view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailFragment = new AttendanceReportingFragment();
                Bundle bundle = new Bundle();
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();

            }

        });

        return view;
    }


}
