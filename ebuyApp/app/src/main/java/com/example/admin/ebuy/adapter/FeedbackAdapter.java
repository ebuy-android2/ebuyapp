package com.example.admin.ebuy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.FeedBackData;
import com.example.admin.ebuy.view.EBCustomFont;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    BaseFragment baseFragment;
    ArrayList<FeedBackData> listFeedBackData;


    public void setListFeedBackData(ArrayList<FeedBackData> listFeedBackData) {
        this.listFeedBackData = listFeedBackData;
    }

    public FeedbackAdapter(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(baseFragment.getContext());
        View view = layoutInflater.inflate(R.layout.feedback_item,viewGroup,false);
        return new FeedbackAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(listFeedBackData.get(i));
    }

    @Override
    public int getItemCount() {
        return listFeedBackData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        EBCustomFont txtNameShop,txtComment;
        CircleImageView imgAvatarShop;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            txtComment = (EBCustomFont)itemView.findViewById(R.id.txtComment);
            txtNameShop = (EBCustomFont)itemView.findViewById(R.id.txtNameShop);
            imgAvatarShop = (CircleImageView)itemView.findViewById(R.id.imgAvatarShop);
        }
        public void bind(FeedBackData feedBackData)
        {
            ratingBar.setRating(feedBackData.getFeedback());
            txtNameShop.setText(feedBackData.getName());
            txtComment.setText(feedBackData.getComment());
            Picasso.with(baseFragment.getContext())
                    .load(feedBackData.getAvatar())
                    .placeholder(R.mipmap.logo)
                    .into(imgAvatarShop);

        }
    }
}
