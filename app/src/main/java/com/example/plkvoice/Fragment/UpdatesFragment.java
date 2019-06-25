package com.example.plkvoice.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.example.plkvoice.Interface.UILConfig;
import com.example.plkvoice.Interface.updates;
import com.example.plkvoice.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class UpdatesFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
    final static String url = "https://pnpapp.000webhostapp.com/updates.php";

    private ArrayList<updates> optionsList;
    private ListView lv;

    FunDapter<updates> adapter;

    View view;
    public UpdatesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_options, container, false);
        ImageLoader.getInstance().init(UILConfig.config(UpdatesFragment.this.getActivity()));
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(UpdatesFragment.this.getActivity(), this);
        taskRead.execute(url);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Update(s)");
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
            Toast.makeText(UpdatesFragment.this.getActivity(), "There are no Update(s)", Toast.LENGTH_SHORT).show();
        }

        else
        {

            optionsList = new JsonConverter<updates>().toArrayList(s, updates.class);
            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.tvSummary, new StringExtractor<updates>() {
                @Override
                public String getStringValue(updates item, int position) {
                        return item.summary;
                }
            });

            adapter = new FunDapter<>(UpdatesFragment.this.getActivity(), optionsList,
                    R.layout.fragment_updates_row, dic);
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
        updates updates = optionsList.get(position);

        AlertDialog.Builder dialogBox = new AlertDialog.Builder(this.getActivity());
        dialogBox.setMessage(updates.updates)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//closes the dialog box
                    }
                });

        AlertDialog dialog = dialogBox.create();
        dialog.setTitle("Update");
        dialog.show();
        dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#808080"));

    }
}
