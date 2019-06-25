package com.example.plkvoice.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.plkvoice.Interface.AdminOptions;
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


public class AdminIssuesFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
    final static String url = "https://pnpapp.000webhostapp.com/AdminIssues.php";
    public static final String PREFS = "preferencesfFile";

    private ArrayList<AdminOptions> optionsList;
    private ListView lv;
    Serializable serializable;
    String type;


    FunDapter<AdminOptions> adapter;

    View view;
    public AdminIssuesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_options, container, false);
        ImageLoader.getInstance().init(UILConfig.config(AdminIssuesFragment.this.getActivity()));
        final HashMap postData = new HashMap();
        serializable = getArguments().getSerializable("options");
        Options options = (Options) getArguments().getSerializable("options");
        type = options.options_code;
        Toast.makeText(AdminIssuesFragment.this.getActivity(), type, Toast.LENGTH_SHORT).show();
        postData.put("type",type);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(AdminIssuesFragment.this.getActivity(),postData, this);
        taskRead.execute(url);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle( type + " Complaints");

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
            Toast.makeText(AdminIssuesFragment.this.getActivity(), "There are no categories", Toast.LENGTH_SHORT).show();
        }

        else
        {
            optionsList = new JsonConverter<AdminOptions>().toArrayList(s, AdminOptions.class);
            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.phone, new StringExtractor<AdminOptions>() {
                @Override
                public String getStringValue(AdminOptions item, int position) {
                        return "Phone Number : " + item.phone;
                }
            });

            dic.addStringField(R.id.name, new StringExtractor<AdminOptions>() {
                @Override
                public String getStringValue(AdminOptions item, int position) {
                    return "Name : " + item.name;
                }
            });

            dic.addStringField(R.id.other, new StringExtractor<AdminOptions>() {
                @Override
                public String getStringValue(AdminOptions item, int position) {
                    return "More : " + item.other;
                }
            });

            dic.addStringField(R.id.address, new StringExtractor<AdminOptions>() {
                @Override
                public String getStringValue(AdminOptions item, int position) {
                    return "Address : " + item.address;
                }
            });

            dic.addDynamicImageField(R.id.ivImage, new StringExtractor<AdminOptions>() {
                @Override
                public String getStringValue(AdminOptions item, int position) {
                    return item.img;
                }
            }, new DynamicImageLoader() {
                @Override
                public void loadImage(String url, ImageView img) {
                    //Set image
                    ImageLoader.getInstance().displayImage(url, img);
                }
            });

            adapter = new FunDapter<>(AdminIssuesFragment.this.getActivity(), optionsList,
                    R.layout.fragment_options_admin_row, dic);
            lv = view.findViewById(R.id.lvOptions);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(this);
        }


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AdminOptions receivedMessage = optionsList.get(position);
        AlertDialog.Builder dialogBox = new AlertDialog.Builder(this.getActivity());
        dialogBox.setMessage(receivedMessage.issue)
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//closes the dialog box
                    }
                });

        AlertDialog dialog = dialogBox.create();
        dialog.setTitle("Received Message");
        dialog.show();


    }
}
