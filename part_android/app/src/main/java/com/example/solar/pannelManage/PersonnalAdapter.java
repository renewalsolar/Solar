package com.example.solar.pannelManage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solar.Models.PannelInfo;
import com.example.solar.Models.UserInfo;
import com.example.solar.R;

import java.util.List;

public class PersonnalAdapter extends RecyclerView.Adapter<PersonnalAdapter.ViewHolder> {

    private List<PannelInfo> panelList;
    private Context context;
    private UserInfo user;

    public PersonnalAdapter(List<PannelInfo> panelList, UserInfo user) {
        this.panelList = panelList;
        this.user = user;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout listLayout, list_item_holder, list_item_holder2;
        public TextView auth_id, maxOutput, address;
        public Button edit, del;

        public ViewHolder(View v) {
            super(v);

            listLayout = (LinearLayout) v.findViewById(R.id.list_layout);

            list_item_holder = (LinearLayout) v.findViewById(R.id.list_item_holder);
            list_item_holder2 = (LinearLayout) v.findViewById(R.id.list_item_holder2);

            auth_id = (TextView) v.findViewById(R.id.list_title);
            maxOutput = (TextView) v.findViewById(R.id.list_second);
            address = (TextView) v.findViewById(R.id.list_third);

            edit = (Button) v.findViewById(R.id.btn_panel_edit);
            del = (Button) v.findViewById(R.id.btn_panel_del);
        }
    }

    @Override
    public PersonnalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PersonnalAdapter.ViewHolder holder, final int position) {
        final PannelInfo panel = panelList.get(position);
        holder.auth_id.setText(panel.get_id());
        holder.maxOutput.setText(panel.getMaxOutput());
        holder.address.setText(panel.getAddress());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("USER_INFO", user);
                intent.putExtra("PANNEL_INFO", panel);
                intent.putExtra("INDEX", position);
                ((Activity)context).startActivityForResult(intent,1004);
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteFunc(context, panel.get_id());
               // notifyItemRemoved(position);
                panelList.remove(position);
                notifyDataSetChanged();


            }
        });
    }

    @Override
    public int getItemCount() {
        return panelList.size();
    }
}