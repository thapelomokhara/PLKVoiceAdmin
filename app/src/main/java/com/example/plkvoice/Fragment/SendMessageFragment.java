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
import android.widget.Toast;

import com.example.plkvoice.R;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.HashMap;


public class SendMessageFragment extends Fragment {
    View view;
    final String LOG = "SendMessage.this";
    String type;
    Serializable serializable;
    static EditText message, num;
    Button button,prevButton;


    public SendMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mesage, container, false);
        message = view.findViewById(R.id.Message);
        num = view.findViewById(R.id.Num);
        button = view.findViewById(R.id.btnSubmit);
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);

        toolbar.setTitle("Send Message");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!message.getText().toString().trim().equals("")) {

                    if(!num.getText().toString().trim().equals("")){
                        if(num.getText().toString().length() == 10) {

                            HashMap postData = new HashMap();
                            postData.put("message", message.getText().toString());
                            postData.put("number", num.getText().toString());

                            PostResponseAsyncTask cardTask = new PostResponseAsyncTask(SendMessageFragment.this.getActivity(), postData, new AsyncResponse() {
                                @Override
                                public void processFinish(String s) {
                                    Log.d(LOG, s);
                                    if (s.contains("success")) {
                                       Toast toast = Toast.makeText(SendMessageFragment.this.getActivity(), "Message Sent Successfully.", Toast.LENGTH_SHORT);
                                       toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                       toast.show();
                                        HomeFragment deliveryFragment = new HomeFragment();
                                        FragmentManager manager = getFragmentManager();
                                        manager.beginTransaction().replace(R.id.content_main1, deliveryFragment,
                                                deliveryFragment.getTag()).commit();
                                    }
                                }
                            });

                            cardTask.execute("https://pnpapp.000webhostapp.com/SendMessage.php");
                        } else {
                            Toast.makeText(SendMessageFragment.this.getActivity(),"Your Phone Number must have 10 digits.",Toast.LENGTH_SHORT).show();
                        }
                } else {
                        Toast.makeText(SendMessageFragment.this.getActivity(),"Please capture your Phone Number.",Toast.LENGTH_SHORT).show();
                    } }
                    else {
                        if(num.getText().toString().isEmpty()){
                            Toast.makeText(SendMessageFragment.this.getActivity(), "Please capture all the fields.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SendMessageFragment.this.getActivity(), "Please capture a Message.", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

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

    public void onBackPressed() {
        Fragment detailFragment = new MessageOptionsFragment();
        Bundle bundle = new Bundle();
        detailFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_main1, detailFragment)
                .addToBackStack("HomeDetail").commit();
    }

}
