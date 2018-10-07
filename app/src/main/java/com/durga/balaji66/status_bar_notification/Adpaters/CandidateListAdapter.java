package com.durga.balaji66.status_bar_notification.Adpaters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durga.balaji66.status_bar_notification.Models.CandidateListModel;
import com.durga.balaji66.status_bar_notification.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.MyViewHolder> {

private Context context;
private List<CandidateListModel> mCandidate_ist;

    public CandidateListAdapter(Context context, List<CandidateListModel> mCandidate_ist) {
        this.context = context;
        this.mCandidate_ist = mCandidate_ist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.candidate_list,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        CandidateListModel candidateListModel =mCandidate_ist.get(i);
        myViewHolder.mUId.setText(candidateListModel.getmUId());
        myViewHolder.mName.setText(candidateListModel.getmName());
        myViewHolder.mPhone.setText(candidateListModel.getmPhone());

    }

    @Override
    public int getItemCount() {
        return mCandidate_ist.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mUId, mName, mPhone;
         MyViewHolder(@NonNull View itemView) {
            super(itemView);
        mUId =itemView.findViewById(R.id.textViewUId);
        mName =itemView.findViewById(R.id.textViewName);
        mPhone =itemView.findViewById(R.id.textViewPhone);



        }


    }
}
