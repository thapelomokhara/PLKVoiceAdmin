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
import com.example.plkvoice.Interface.Attendance;
import com.example.plkvoice.Interface.UILConfig;
import com.example.plkvoice.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class AttendanceFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
    final static String url = "https://pnpapp.000webhostapp.com/options.php";
    final String LOG = "SendMessage.this";
    private ArrayList<Attendance> optionsList;
    private ListView lv;

    FunDapter<Attendance> adapter;
    Button button,prevButton;
    static EditText search;


    View view;
    public AttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_attendance_options, container, false);
        ImageLoader.getInstance().init(UILConfig.config(AttendanceFragment.this.getActivity()));
        search = view.findViewById(R.id.Search);
        button = view.findViewById(R.id.btnSearch);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!search.getText().toString().isEmpty()) {
                    if(search.getText().toString().length() == 10){
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(button.getWindowToken(), 0);
                    HashMap postData = new HashMap();
                    postData.put("search", search.getText().toString());
                    PostResponseAsyncTask cardTask = new PostResponseAsyncTask(AttendanceFragment.this.getActivity(), postData, AttendanceFragment.this);
                    cardTask.execute("https://pnpapp.000webhostapp.com/attendance.php");
                } else{
                        Toast.makeText(AttendanceFragment.this.getActivity(), "Your Phone Numbers should have 10 digits.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AttendanceFragment.this.getActivity(), "Please Search with Phone Numbers.", Toast.LENGTH_SHORT).show();
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



    @Override
    public void processFinish(String s) {

        if (s.contains("null"))
        {
            Toast.makeText(AttendanceFragment.this.getActivity(), "Sorry!! Your issue has not been attended Yet.", Toast.LENGTH_SHORT).show();
        }

        else
        {

            optionsList = new JsonConverter<Attendance>().toArrayList(s, Attendance.class);
            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.tvReport, new StringExtractor<Attendance>() {
                @Override
                public String getStringValue(Attendance item, int position) {
                        return "Report :" + item.report;
                }
            });

            dic.addStringField(R.id.tvAttedance, new StringExtractor<Attendance>() {
                @Override
                public String getStringValue(Attendance item, int position) {
                    return "Attended : " + item.attendance;
                }
            });



            adapter = new FunDapter<>(AttendanceFragment.this.getActivity(), optionsList,
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
        Attendance attendance = optionsList.get(position);

        AlertDialog.Builder dialogBox = new AlertDialog.Builder(this.getActivity());
        dialogBox.setMessage(attendance.report)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//closes the dialog box
                    }
                });

        AlertDialog dialog = dialogBox.create();
        dialog.setTitle("Report");
        dialog.show();
        dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#808080"));
    }
}
