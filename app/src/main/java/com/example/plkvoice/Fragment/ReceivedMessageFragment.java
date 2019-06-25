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
import com.example.plkvoice.Interface.ReceivedMessage;
import com.example.plkvoice.Interface.UILConfig;
import com.example.plkvoice.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class ReceivedMessageFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {
    final static String url = "https://pnpapp.000webhostapp.com/ReceivedMessage.php";

    private ArrayList<ReceivedMessage> optionsList;
    private ListView lv;

    FunDapter<ReceivedMessage> adapter;

    View view;
    public ReceivedMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_options, container, false);
        ImageLoader.getInstance().init(UILConfig.config(ReceivedMessageFragment.this.getActivity()));
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(ReceivedMessageFragment.this.getActivity(), this);
        taskRead.execute(url);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Received Message(s)");
        Button back =  view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailFragment = new MessageOptionsFragment();
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
            Toast.makeText(ReceivedMessageFragment.this.getActivity(), "There are no categories", Toast.LENGTH_SHORT).show();
        }

        else
        {

            optionsList = new JsonConverter<ReceivedMessage>().toArrayList(s, ReceivedMessage.class);
            BindDictionary dic = new BindDictionary();

            dic.addStringField(R.id.tvOptions, new StringExtractor<ReceivedMessage>() {
                @Override
                public String getStringValue(ReceivedMessage item, int position) {
                        return item.message_summary;
                }
            });

            adapter = new FunDapter<>(ReceivedMessageFragment.this.getActivity(), optionsList,
                    R.layout.fragment_message_row, dic);
            lv = view.findViewById(R.id.lvOptions);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(this);
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ReceivedMessage receivedMessage = optionsList.get(position);

        AlertDialog.Builder dialogBox = new AlertDialog.Builder(this.getActivity());
        dialogBox.setMessage(receivedMessage.message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//closes the dialog box
                    }
                });

        AlertDialog dialog = dialogBox.create();
        dialog.setTitle("Received Message");
        dialog.show();
        dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#808080"));
    }
}
