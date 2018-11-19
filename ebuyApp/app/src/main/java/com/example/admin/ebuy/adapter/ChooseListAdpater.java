package com.example.admin.ebuy.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.activity.SupportActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.list.ChooseListTypeFragment;
import com.example.admin.ebuy.list.ChooseListTypeProductFragment;
import com.example.admin.ebuy.model.ProductData;
import com.example.admin.ebuy.model.TypeData;
import com.example.admin.ebuy.model.TypeProductData;
import com.example.admin.ebuy.user.AddProductFragment;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.PrefUtils;
import com.example.admin.ebuy.view.EBCustomFont;

import java.util.ArrayList;

public class ChooseListAdpater extends RecyclerView.Adapter<ChooseListAdpater.ViewHolder> {

    private BaseFragment baseFragment;
    private ArrayList<ProductData> listCategoryProduct;
    private ArrayList<TypeData> listType;
    private ArrayList<TypeProductData> listTypeProduct;

    public ChooseListAdpater(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    public ArrayList<ProductData> getListCategoryProduct() {
        return listCategoryProduct;
    }

    public void setListCategoryProduct(ArrayList<ProductData> listCategoryProduct) {
        this.listCategoryProduct = listCategoryProduct;
        notifyDataSetChanged();
    }

    public ArrayList<TypeData> getListType() {
        return listType;
    }

    public void setListType(ArrayList<TypeData> listType) {
        this.listType = listType;
        notifyDataSetChanged();
    }

    public ArrayList<TypeProductData> getListTypeProduct() {
        return listTypeProduct;
    }

    public void setListTypeProduct(ArrayList<TypeProductData> listTypeProduct) {
        this.listTypeProduct = listTypeProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(baseFragment.getContext());
        View view = layoutInflater.inflate(R.layout.list_fragment_item, viewGroup, false);
        return new ChooseListAdpater.ViewHolder(view);
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
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout btnList;
        EBCustomFont txtNameList, txtNextList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnList = (RelativeLayout)itemView.findViewById(R.id.btnList);
            txtNameList = (EBCustomFont)itemView.findViewById(R.id.txtNameList);
            txtNextList = (EBCustomFont)itemView.findViewById(R.id.txtNextList);
            btnList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listCategoryProduct!=null){
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", listCategoryProduct.get(getAdapterPosition()).getId());
                        bundle.putString("name",listCategoryProduct.get(getAdapterPosition()).getName());
                        PrefUtils.getInstance().putString(PrefUtils.ID_LIST,listCategoryProduct.get(getAdapterPosition()).getId()+"");
                        // chuyển fragment
                        Navigator.getInstance().startFragment(baseFragment.getContext(), ChooseListTypeFragment.TAG, SupportActivity.class, bundle);


                    }else if (listType != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", listType.get(getAdapterPosition()).getId());
                        bundle.putString("name",listType.get(getAdapterPosition()).getName());
                        PrefUtils.getInstance().putString(PrefUtils.ID_TYPE,listType.get(getAdapterPosition()).getId()+"");
                        // chuyển fragment
                        Navigator.getInstance().startFragment(baseFragment.getContext(), ChooseListTypeProductFragment.TAG, SupportActivity.class, bundle);

                    }else if(listTypeProduct!=null){
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", listTypeProduct.get(getAdapterPosition()).getId());
                        bundle.putString("name",listTypeProduct.get(getAdapterPosition()).getName());
                        PrefUtils.getInstance().putString(PrefUtils.ID_TYPE_PRODUCT, listTypeProduct.get(getAdapterPosition()).getId()+"");
                        Navigator.getInstance().startFragment(baseFragment.getContext(), AddProductFragment.TAG, UserActivity.class,bundle);
                    }
                }
            });
        }
        public void bindView(ProductData productData) {
            txtNameList.setText(productData.getName());
        }
        public void bindView(TypeData typeData) {
            txtNameList.setText(typeData.getName());
        }
        public void bindView(TypeProductData typeProductData) {
            txtNextList.setVisibility(View.GONE);
            txtNameList.setText(typeProductData.getName());

        }
    }


}
