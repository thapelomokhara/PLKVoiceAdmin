package com.example.plkvoice.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.example.plkvoice.Interface.Attendance;
import com.example.plkvoice.Interface.AttendanceAdmin;
import com.example.plkvoice.Interface.Options;
import com.example.plkvoice.Interface.UILConfig;
import com.example.plkvoice.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class AdminReportFragment extends Fragment {
    final static String url = "https://pnpapp.000webhostapp.com/UpdateReport.php";
    final String LOG = "SendMessage.this";
    private ArrayList<Attendance> optionsList;
    private ListView lv;

    FunDapter<Attendance> adapter;
    Button button,prevButton;
    static EditText search;
    Serializable serializable;
    String phone;
    TextView phoneNum,report;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    View view;
    public AdminReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_attendance, container, false);
        ImageLoader.getInstance().init(UILConfig.config(AdminReportFragment.this.getActivity()));
        serializable = getArguments().getSerializable("options");
        AttendanceAdmin options = (AttendanceAdmin) getArguments().getSerializable("options");
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Attend Report");
        phone=options.attendance;
        button = view.findViewById(R.id.btnSubmit);
        phoneNum = view.findViewById(R.id.Reporter);
        report = view.findViewById(R.id.Report);

        phoneNum.setText("Reporter Phone Number : " + options.attendance);
        report.setText("Report : " + options.report);
        radioGroup = view.findViewById(R.id.radioB);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = view.findViewById(selectedId);

                HashMap postData = new HashMap();
                postData.put("phone", phone);
                postData.put("attendant", radioButton.getText());

                PostResponseAsyncTask cardTask = new PostResponseAsyncTask(AdminReportFragment.this.getActivity(), postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        if (s.contains("success")) {
                            Toast toast = Toast.makeText(AdminReportFragment.this.getActivity(), "Done", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                            toast.show();
                            HomeFragment deliveryFragment = new HomeFragment();
                            FragmentManager manager = getFragmentManager();
                            manager.beginTransaction().replace(R.id.content_main1, deliveryFragment,
                                    deliveryFragment.getTag()).commit();
                        }

                    }
                });
                cardTask.execute(url);
            }
        });

        Button back =  view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailFragment = new AdminAttendanceFragment();
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
