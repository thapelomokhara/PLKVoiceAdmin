package com.example.plkvoice.Fragment;

import android.content.SharedPreferences;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.example.plkvoice.Interface.Options;
import com.example.plkvoice.Interface.UILConfig;
import com.example.plkvoice.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class OptionsFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
    final static String url = "https://pnpapp.000webhostapp.com/options.php";
    public static final String PREFS = "preferencesfFile";


    private ArrayList<Options> optionsList;
    private ListView lv;

    FunDapter<Options> adapter;

    View view;
    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issue_options, container, false);
        ImageLoader.getInstance().init(UILConfig.config(OptionsFragment.this.getActivity()));
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(OptionsFragment.this.getActivity(), this);
        taskRead.execute(url);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Options");

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(toolbar.getWindowToken(),0);

        Button back =  view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailFragment = new HomeFragment();
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
            Toast.makeText(OptionsFragment.this.getActivity(), "There are no categories", Toast.LENGTH_SHORT).show();
        }

        else
        {

            optionsList = new JsonConverter<Options>().toArrayList(s, Options.class);
            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.tvOptions, new StringExtractor<Options>() {
                @Override
                public String getStringValue(Options item, int position) {
                        return item.options_code;
                }
            });
            dic.addDynamicImageField(R.id.ivImage, new StringExtractor<Options>() {
                @Override
                public String getStringValue(Options item, int position) {
                    return item.img_url;
                }
            }, new DynamicImageLoader() {
                        @Override
                        public void loadImage(String url, ImageView img) {
                            //Set image
                            ImageLoader.getInstance().displayImage(url, img);
                        }
                    });

            adapter = new FunDapter<>(OptionsFragment.this.getActivity(), optionsList,
                    R.layout.fragment_options_row, dic);
            lv = view.findViewById(R.id.lvOptions);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(this);
        }


    }

    public void onBackPressed() {
        Fragment detailFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        detailFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                .addToBackStack("HomeDetail").commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Options options = optionsList.get(position);
        SharedPreferences preferences = OptionsFragment.this.getActivity().getSharedPreferences(PREFS, 0);
        final String customer = preferences.getString("username", null);

        if(customer.equals("ADMIN")){
            if(options.options_code.equals("Attendance and Reporting")){
                Fragment detailFragment = new AdminAttendanceFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("options", options);
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();
            }else if(options.options_code.equals("Updates")){
                Fragment detailFragment = new SendUpdateFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("options", options);
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();
            }else{
                Fragment detailFragment = new AdminIssuesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("options", options);
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();
            }
        }else{
            if(options.options_code.equals("Attendance and Reporting")){
                Fragment detailFragment = new AttendanceReportingFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("options", options);
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();
            }else if(options.options_code.equals("Updates")){
                Fragment detailFragment = new UpdatesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("options", options);
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();
            }else{
                Fragment detailFragment = new IssueFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("options", options);
                detailFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                        .addToBackStack("HomeDetail").commit();
            }
        }


    }
}