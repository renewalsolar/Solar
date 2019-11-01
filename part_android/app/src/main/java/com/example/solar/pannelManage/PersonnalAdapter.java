package com.example.solar.pannelManage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.solar.Models.PannelInfo;
import com.example.solar.R;

import java.util.List;

public class PersonnalAdapter extends RecyclerView.Adapter<PersonnalAdapter.ViewHolder> {

private List<PannelInfo> panelList;

public PersonnalAdapter(List<PannelInfo> panelList) {
        this.panelList = panelList;
        }
public void setListViewAdapter(List<PannelInfo> panelList) {
        this.panelList = panelList;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PersonnalAdapter.ViewHolder holder, final int position) {
            PannelInfo panel = panelList.get(position);
            holder.auth_id.setText(panel.get_id());
            holder.maxOutput.setText(panel.getMaxOutput());
            holder.address.setText(panel.getAddress());

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ERRRRR",String.valueOf(position) + "     " );
                }
            });

            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }

    @Override
    public int getItemCount() {
        return panelList.size();
    }
}