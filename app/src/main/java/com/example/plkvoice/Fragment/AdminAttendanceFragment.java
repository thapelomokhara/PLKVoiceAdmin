package com.example.plkvoice.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.example.plkvoice.Interface.AttendanceAdmin;
import com.example.plkvoice.Interface.UILConfig;
import com.example.plkvoice.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class AdminAttendanceFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
    final static String url = "https://pnpapp.000webhostapp.com/AdminAttandance.php";
    final String LOG = "SendMessage.this";
    private ArrayList<AttendanceAdmin> optionsList;
    private ListView lv;

    FunDapter<AttendanceAdmin> adapter;
    Button button,prevButton;
    static EditText search;


    View view;
    public AdminAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_options, container, false);
        ImageLoader.getInstance().init(UILConfig.config(AdminAttendanceFragment.this.getActivity()));
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(AdminAttendanceFragment.this.getActivity(), this);
        taskRead.execute(url);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Reports");

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



    @Override
    public void processFinish(String s) {

        if (s.contains("null"))
        {
            Toast.makeText(AdminAttendanceFragment.this.getActivity(), "No Issues.", Toast.LENGTH_SHORT).show();
        }

        else
        {

            optionsList = new JsonConverter<AttendanceAdmin>().toArrayList(s, AttendanceAdmin.class);
            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.tvReport, new StringExtractor<AttendanceAdmin>() {
                @Override
                public String getStringValue(AttendanceAdmin item, int position) {
                        return "Reporter Phone Number :" + item.attendance;
                }
            });

            dic.addStringField(R.id.tvAttedance, new StringExtractor<AttendanceAdmin>() {
                @Override
                public String getStringValue(AttendanceAdmin item, int position) {
                    return "Report : " + item.report;
                }
            });



            adapter = new FunDapter<>(AdminAttendanceFragment.this.getActivity(), optionsList,
                    R.layout.fragment_attandance_row, dic);
            lv = view.findViewById(R.id.lvOptions);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(this);
        }


    }

    public void onBackPressed() {
        Fragment detailFragment = new OptionsFragment();
        Bundle bundle = new Bundle();
        detailFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                .addToBackStack("HomeDetail").commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AttendanceAdmin options = optionsList.get(position);
        Fragment detailFragment = new AdminReportFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("options", options);
        detailFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                .addToBackStack("HomeDetail").commit();

    }
}
