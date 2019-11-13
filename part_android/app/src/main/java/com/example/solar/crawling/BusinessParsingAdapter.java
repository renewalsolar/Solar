package com.example.solar.crawling;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solar.R;

import java.util.ArrayList;

public class BusinessParsingAdapter extends RecyclerView.Adapter<BusinessParsingAdapter.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<ItemObject> mList;
    private Context context;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView imageView_img;
        private TextView textView_title, textView_contents;

        public ViewHolder(final View itemView) {
            super(itemView);

            //imageView_img = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            textView_contents = (TextView) itemView.findViewById(R.id.textView_contents);
            //texView_director = (TextView) itemView.findViewById(R.id.textView_director);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("link", mList.get(getAdapterPosition()).getDetail_link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(getAdapterPosition()).getDetail_link()));

                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }
    }

    //생성자
    public BusinessParsingAdapter(ArrayList<ItemObject> list, Context context) {
        this.mList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BusinessParsingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessParsingAdapter.ViewHolder holder, int position) {

        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        holder.textView_contents.setText(String.valueOf(mList.get(position).getContents()));
        //다 해줬는데도 GlideApp 에러가 나면 rebuild project를 해주자.
        /*GlideApp.with(holder.itemView).load(mList.get(position).getImg_url())
                .override(300,400)
                .into(holder.imageView_img);*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}