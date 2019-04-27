package com.enigneerbabu.demos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enigneerbabu.demos.R;
import com.enigneerbabu.demos.model.detaillist;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private Context context;
    private List<detaillist> list;

    public DetailAdapter(Context context, List<detaillist> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        detaillist detaillist = list.get(position);

        holder.layout_Name.setText(detaillist.getName());
        holder.email_name.setText(String.valueOf(detaillist.getEmail()));
        holder.layout_description.setText(String.valueOf(detaillist.getBody()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView layout_Name, email_name, layout_description;

        public ViewHolder(View itemView) {
            super(itemView);

            layout_Name = itemView.findViewById(R.id.layout_Name);
            email_name = itemView.findViewById(R.id.email_name);
            layout_description = itemView.findViewById(R.id.layout_description);
        }
    }

}
