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

import java.util.List;


public class ProductAdapter extends BaseAdapter {
    List<Product> products;
    Context mContext;


    public ProductAdapter(Context context, List<Product> productList) {
        super();
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
    public View getView(int position, View view, ViewGroup parent) {

        Product product = products.get(position);
        view = LayoutInflater.from(mContext).inflate(R.layout.single_product, null);

        TextView productname = (TextView) view.findViewById(R.id.productshop);
        TextView productshop = (TextView) view.findViewById(R.id.productname);
        TextView productprice = (TextView) view.findViewById(R.id.productprice);
        TextView productquantity = (TextView) view.findViewById(R.id.product_quantity);

        productname.setText(product.getProductName());
        productshop.setText(product.getStore());
        productprice.setText(Double.toString(product.getPrice()));
        productquantity.setText(Integer.toString(product.getQuantity()));

        return view;
    }
}
