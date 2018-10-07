package com.durga.balaji66.status_bar_notification.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.durga.balaji66.status_bar_notification.Adpaters.CandidateListAdapter;
import com.durga.balaji66.status_bar_notification.Apis.APIUrl;
import com.durga.balaji66.status_bar_notification.BroadcastReceiverClass;
import com.durga.balaji66.status_bar_notification.MainActivity;
import com.durga.balaji66.status_bar_notification.Models.CandidateListModel;
import com.durga.balaji66.status_bar_notification.R;
import com.durga.balaji66.status_bar_notification.ResponseCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.LiveFolders.INTENT;


public class CandidateListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<CandidateListModel> mList  =new ArrayList<>();
    private CandidateListAdapter candidateListAdapter;
    private BroadcastReceiver broadcastReceiver;
    public static final String DATA_SAVED_BROADCAST = "dataSaved";
    private Context context;
    public CandidateListFragment() {
        // Required empty public constructor
    }

     @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_candidate_list, container, false);
            mRecyclerView =view.findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            getCandidateList();

         getContext().registerReceiver(new BroadcastReceiverClass(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
         broadcastReceiver = new BroadcastReceiver() {
             @Override
             public void onReceive(Context context, Intent intent) {
                 Toast.makeText(getActivity(),"in the receiver",Toast.LENGTH_LONG).show();
                 //getCandidateList();
                              }
         };

         return view;
    }


    public void getCandidateList()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Signing Up...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<ResponseCategory> call = APIUrl.getmInstance().getApi().CandidateList();

        call.enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCategory> call, @NonNull Response<ResponseCategory> response) {

                if(response.code() == 200)
                {
                    progressDialog.dismiss();
                    try {
                        // Here fetching date and storing in arrayList.
                        mList = new ArrayList<CandidateListModel>(Objects.requireNonNull(response.body()).getCategories());
                        //Creating PracticeCategoryAdapter Object.
                        candidateListAdapter = new CandidateListAdapter(getActivity(), mList);
                        //setting recyclerView adapter.
                        mRecyclerView.setAdapter(candidateListAdapter);
                                            } catch (Exception e) {
                        System.out.println("hai" + e);
                    }

                }
                else if( response.code() == 401)
                {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Invalid User Id or password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Please Check After Some Time",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCategory> call, @NonNull Throwable t) {
                progressDialog.dismiss();

            }
        });
    }


}

