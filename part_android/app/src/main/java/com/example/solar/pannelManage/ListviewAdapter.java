package com.example.solar.pannelManage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solar.Models.PannelInfo;
import com.example.solar.R;

import java.util.List;

public class ListviewAdapter extends RecyclerView.Adapter<ListviewAdapter.ViewHolder> {

private List<PannelInfo> productList; // image, cost, title 사용


public ListviewAdapter(List<PannelInfo> productList) {
        this.productList = productList;
        }
public void setProductAdapter(List<PannelInfo> productList) {
        this.productList = productList;
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    //each data item is just a string in this case
    public TextView title, fee;

    public ViewHolder(View v) {
        super(v);

//        title = (TextView) v.findViewById(R.id.card_title);
//        fee = (TextView) v.findViewById(R.id.card_fee);
    }
}



    //Create new views (invoked by the layout manager)
    @Override
    public ListviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);


        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListviewAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        PannelInfo product = productList.get(position);

//        holder.title.setText(product.getName());
//        holder.fee.setText(df.format(product.getCost())+ '원');
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}