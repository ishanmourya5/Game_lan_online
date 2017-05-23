package com.example.ishan.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class user_adapter extends RecyclerView.Adapter<user_adapter.myViewHolder>{

    private List<user> user_list;

    public class myViewHolder extends RecyclerView.ViewHolder{

        public TextView name_tv;
        public TextView email_tv;

        public myViewHolder(View view){
            super(view);
            name_tv = (TextView) view.findViewById(R.id.user_name);
            email_tv = (TextView) view.findViewById(R.id.user_email);
        }
    }

    public user_adapter(List<user> user_list){
        this.user_list = user_list;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        user u = user_list.get(position);
        holder.name_tv.setText(u.getName());
        holder.email_tv.setText(u.getEmail());
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }
}
