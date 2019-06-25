package com.example.plkvoice.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.example.plkvoice.Interface.AttandanceReporting;
import com.example.plkvoice.Interface.UILConfig;
import com.example.plkvoice.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class AttendanceReportingFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
    final static String url = "https://pnpapp.000webhostapp.com/AttandanceReporting.php";

    private ArrayList<AttandanceReporting> optionsList;
    private ListView lv;

    FunDapter<AttandanceReporting> adapter;

    View view;
    public AttendanceReportingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_options, container, false);
        ImageLoader.getInstance().init(UILConfig.config(AttendanceReportingFragment.this.getActivity()));
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(AttendanceReportingFragment.this.getActivity(), this);
        taskRead.execute(url);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance and Reporting");
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
            Toast.makeText(AttendanceReportingFragment.this.getActivity(), "No report(s) matching that phone number", Toast.LENGTH_SHORT).show();
        }

        else
        {

            optionsList = new JsonConverter<AttandanceReporting>().toArrayList(s, AttandanceReporting.class);
            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.tvOptions, new StringExtractor<AttandanceReporting>() {
                @Override
                public String getStringValue(AttandanceReporting item, int position) {
                        return item.attandanceReporting;
                }
            });

            dic.addDynamicImageField(R.id.ivImage, new StringExtractor<AttandanceReporting>() {
                @Override
                public String getStringValue(AttandanceReporting item, int position) {
                    return item.img_url;
                }
            }, new DynamicImageLoader() {
                @Override
                public void loadImage(String url, ImageView img) {
                    //Set image
                    ImageLoader.getInstance().displayImage(url, img);
                }
            });


            adapter = new FunDapter<>(AttendanceReportingFragment.this.getActivity(), optionsList,
                    R.layout.fragment_message_row, dic);
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
        AttandanceReporting receivedMessage = optionsList.get(position);

        if(receivedMessage.attandanceReporting.equals("Reporting"))
        {
            Fragment detailFragment = new ReportingFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("options", receivedMessage);
            detailFragment.setArguments(bundle);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                    .addToBackStack("HomeDetail").commit();
        }else{
            Fragment detailFragment = new AttendanceFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("options", receivedMessage);
            detailFragment.setArguments(bundle);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                    .addToBackStack("HomeDetail").commit();
        }

    }
}
