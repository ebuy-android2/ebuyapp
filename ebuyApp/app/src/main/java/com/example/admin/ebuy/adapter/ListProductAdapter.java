package com.example.admin.ebuy.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.activity.SupportActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.ProductDetailData;
import com.example.admin.ebuy.model.home.ProductDetailFragment;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.view.EBCustomFont;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {
    BaseFragment baseFragment;
    ArrayList<ProductDetailData> listProduct;

    public void setListProduct(ArrayList<ProductDetailData> listProduct) {
        this.listProduct = listProduct;
    }

    public ListProductAdapter(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(baseFragment.getContext());
        View view = layoutInflater.inflate(R.layout.product_detail_item1,viewGroup,false);
        return new ListProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(listProduct.get(i));
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EBCustomFont txtNamePro, txtPricePro,txtNumLike,txtNumStar;
        ImageView imageView;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNamePro = (EBCustomFont) itemView.findViewById(R.id.txtNamePro);
            txtPricePro = (EBCustomFont) itemView.findViewById(R.id.pricePro);
            imageView = (ImageView)itemView.findViewById(R.id.imgProduct);
            txtNumLike = (EBCustomFont)itemView.findViewById(R.id.txtNumLike);
            txtNumStar = (EBCustomFont)itemView.findViewById(R.id.txtNumStar);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();
                    String data = gson.toJson(listProduct.get(getAdapterPosition()));
                    Bundle bundle = new Bundle();
                    bundle.putString("data",data);
                    Navigator.getInstance().startFragment(baseFragment.getContext(),ProductDetailFragment.TAG,SupportActivity.class,bundle);
                }
            });

        }
        private void bind(ProductDetailData productDetailData)
        {
            Picasso.with(baseFragment.getContext())
                    .load(productDetailData.getImage())
                    .placeholder(R.mipmap.logo)
                    .into(imageView);
            txtNamePro.setText(productDetailData.getName());
            txtPricePro.setText(productDetailData.getPrice()+"");
            txtNumLike.setText(productDetailData.getNumLike()+"");
            txtNumStar.setText("("+productDetailData.getNumFeedback()+")");
            ratingBar.setRating(productDetailData.getNumStar());

        }
    }
}
