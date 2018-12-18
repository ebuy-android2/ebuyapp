package com.example.admin.ebuy.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.activity.SupportActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.ProductData;
import com.example.admin.ebuy.model.StoreData;
import com.example.admin.ebuy.model.TypeData;
import com.example.admin.ebuy.model.TypeProductData;
import com.example.admin.ebuy.home.TypeFragment;
import com.example.admin.ebuy.home.TypeProductFragment;
import com.example.admin.ebuy.util.Navigator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ListCategoryProductAdapter extends RecyclerView.Adapter<ListCategoryProductAdapter.ViewHolder> {
    private BaseFragment baseFragment;
    private ArrayList<ProductData> listCategoryProduct;
    private ArrayList<TypeData> listType;
    private ArrayList<TypeProductData> listTypeProduct;
    private ArrayList<StoreData> listStore;

    public void setListStore(ArrayList<StoreData> listStore) {
        this.listStore = listStore;
    }

    public void setListTypeProduct(ArrayList<TypeProductData> listTypeProduct) {
        this.listTypeProduct = listTypeProduct;
    }

    public void setListType(ArrayList<TypeData> listType) {
        this.listType = listType;
    }

    public ListCategoryProductAdapter(ArrayList<TypeData> listType) {
        this.listType = listType;
    }

    public ArrayList<ProductData> getListCategoryProduct() {
        return listCategoryProduct;
    }

    public void setListCategoryProduct(ArrayList<ProductData> listCategoryProduct) {
        this.listCategoryProduct = listCategoryProduct;
        notifyDataSetChanged();
    }


    public ListCategoryProductAdapter(BaseFragment baseFragment, ArrayList<ProductData> listProduct) {
        this.baseFragment = baseFragment;
        this.listCategoryProduct = listProduct;


    }

    @NonNull
    @Override
    public ListCategoryProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(baseFragment.getContext());
        View view = layoutInflater.inflate(R.layout.category_product_item, viewGroup, false);


        return new ListCategoryProductAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(listCategoryProduct!=null)
        {
            viewHolder.bindView(listCategoryProduct.get(i));
        }
       else if (listType!=null)
        {
            viewHolder.bindView(listType.get(i));
        }
        else if(listTypeProduct!=null)
        {
            viewHolder.bindView(listTypeProduct.get(i));
        }
        else if(listStore!=null)
        {
            viewHolder.bindView(listStore.get(i));
        }


    }

    @Override
    public int getItemCount() {
        if(listCategoryProduct!=null)
        {
            return listCategoryProduct.size();
        }
        else if (listType!=null)
        {
            return listType.size();
        }
        else if(listTypeProduct!=null)
        {
            return listTypeProduct.size();
        }
        else if(listStore!=null)
        {
            return listStore.size();
        }
        return 0;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public LinearLayout linearItemCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgCategoryProduct);
            textView = (TextView) itemView.findViewById(R.id.nameCategoryProduct);
            linearItemCategory = (LinearLayout) itemView.findViewById(R.id.linearItemCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listCategoryProduct != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", listCategoryProduct.get(getAdapterPosition()).getId());
                        bundle.putString("name",listCategoryProduct.get(getAdapterPosition()).getName());
                        Navigator.getInstance().startFragment(baseFragment.getContext(), TypeFragment.TAG, SupportActivity.class, bundle);
                    } else if (listType != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", listType.get(getAdapterPosition()).getId());
                        bundle.putString("name",listType.get(getAdapterPosition()).getName());
                        Navigator.getInstance().startFragment(baseFragment.getContext(), TypeProductFragment.TAG, SupportActivity.class, bundle);

                    }

                }
            });


        }


        public void bindView(ProductData productData) {

            Picasso.with(baseFragment.getContext())
                    .load(productData.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            textView.setText(productData.getName());

        }
        public void bindView(TypeData typeData) {

            Picasso.with(baseFragment.getContext())
                    .load(typeData.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            textView.setText(typeData.getName());

        }
        public void bindView(TypeProductData typeProductData) {

            imageView.setVisibility(View.GONE);
            textView.setText(typeProductData.getName());
            textView.setBackgroundResource(R.drawable.bg_border_organ);


        }
        public void bindView(StoreData storeData) {
            if (storeData.getAvatar()!=null&&!storeData.getAvatar().isEmpty()){
                Picasso.with(baseFragment.getContext())
                        .load(storeData.getAvatar())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);

            }else {
                imageView.setImageResource(R.drawable.logo);
            }

            textView.setText(storeData.getName());


        }


    }


}
