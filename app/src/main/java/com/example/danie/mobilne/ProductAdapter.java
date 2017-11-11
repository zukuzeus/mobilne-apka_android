package com.example.danie.mobilne;

/**
 * Created by danie on 09.11.2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends BaseAdapter {
    List<Product> products;
    Context mContext;


    public ProductAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.products = productList;

    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product student = products.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.single_product, null);

        TextView productname = (TextView) convertView.findViewById(R.id.productname);
        TextView productshop = (TextView) convertView.findViewById(R.id.productshop);
        TextView productprice = (TextView) convertView.findViewById(R.id.productprice);
        TextView productquantity = (TextView) convertView.findViewById(R.id.product_quantity);

        productname.setText(student.getProduct());
        productshop.setText(student.getStore());
        productprice.setText(Double.toString(student.getPrice()));
        productquantity.setText(Integer.toString(student.getQuantity()));

        return convertView;
    }
}
