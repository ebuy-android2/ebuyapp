package com.example.admin.ebuy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.model.TypeProductData;
import com.example.admin.ebuy.view.EBCustomFont;

import java.util.List;

/**
 * Created by Donald Trieu on 12/19/18.
 */
public class ChooseListTypeProductAdapter extends ArrayAdapter<TypeProductData>{
    private Context context;
    public ChooseListTypeProductAdapter(@NonNull Context context, int resource, @NonNull List<TypeProductData> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.custom_spinner, parent, false);
        EBCustomFont txtNameFilter = convertView.findViewById(R.id.txtNameFilter);
        txtNameFilter.setText(this.getItem(position).getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.item_spinner_text, parent,false);
        EBCustomFont txtTypeProduct = convertView.findViewById(R.id.txtNameList);
        txtTypeProduct.setText(this.getItem(position).getName());

        return convertView;
    }
}
