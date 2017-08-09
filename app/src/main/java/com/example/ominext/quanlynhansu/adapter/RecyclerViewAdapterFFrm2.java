package com.example.ominext.quanlynhansu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ominext.quanlynhansu.R;
import com.example.ominext.quanlynhansu.model.EmployeesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 8/8/2017.
 */

public class RecyclerViewAdapterFFrm2 extends RecyclerView.Adapter<RecyclerViewAdapterFFrm2.RecyclerViewHolder> {
    private List<EmployeesData> employeesDataList=new ArrayList<>();
    private LayoutInflater inflater;
    Context context;

    public RecyclerViewAdapterFFrm2(List<EmployeesData> employeesDataList, Context context) {
        this.employeesDataList = employeesDataList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewAdapterFFrm2.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_total_age_employee,parent,false);
        return new RecyclerViewAdapterFFrm2.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterFFrm2.RecyclerViewHolder holder, int position) {
        holder.mTVName.setText(employeesDataList.get(position).getmName());
        holder.mTVSex.setText(employeesDataList.get(position).getmSex());
        holder.mTVBirth.setText(employeesDataList.get(position).getmDateOfBirth());
        holder.mTVPhone.setText(employeesDataList.get(position).getmPhone());
        holder.mId.setText(employeesDataList.get(position).getmId()+"");
    }

    @Override
    public int getItemCount() {
        return employeesDataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mId;
        TextView mTVName;
        TextView mTVSex;
        TextView mTVBirth;
        TextView mTVPhone;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mId=(TextView)itemView.findViewById(R.id.tv_id);
            mTVName=(TextView)itemView.findViewById(R.id.tv_name);
            mTVSex=(TextView)itemView.findViewById(R.id.tv_sex);
            mTVBirth=(TextView)itemView.findViewById(R.id.tv_age);
            mTVPhone=(TextView)itemView.findViewById(R.id.tv_phone);
        }
    }
}
