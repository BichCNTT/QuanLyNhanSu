package com.example.ominext.quanlynhansu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ominext.quanlynhansu.MainActivity;
import com.example.ominext.quanlynhansu.R;
import com.example.ominext.quanlynhansu.fragment.AddEmployees;
import com.example.ominext.quanlynhansu.fragment.Fragment1;
import com.example.ominext.quanlynhansu.listener.OnItemClickListener;
import com.example.ominext.quanlynhansu.model.EmployeesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 8/4/2017.
 */

public class RecyclerViewAdapterFFrm1 extends RecyclerView.Adapter<RecyclerViewAdapterFFrm1.RecyclerViewHolder> {
    private OnItemClickListener clickListener;
    private List<EmployeesData> employeesDataList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private int mSelectedItemPosition = -1;

    public RecyclerViewAdapterFFrm1(List<EmployeesData> employeesDataList, Context context) {
        this.employeesDataList = employeesDataList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_employee, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mTVName.setText(employeesDataList.get(position).getmName());
        holder.mTVSex.setText(employeesDataList.get(position).getmSex());
        holder.mTVBirth.setText(employeesDataList.get(position).getmDateOfBirth());
        holder.mTVPhone.setText(employeesDataList.get(position).getmPhone());
        holder.mId.setText(employeesDataList.get(position).getmId() + "");
        holder.bindDataWithViewHolder(employeesDataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return employeesDataList.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mId;
        TextView mTVName;
        TextView mTVSex;
        TextView mTVBirth;
        TextView mTVPhone;
        private EmployeesData mDataItem = null;
        LinearLayout linearLayout;

        //k set cho item view mà set cho linearlayout
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mId = (TextView) itemView.findViewById(R.id.id);
            mTVName = (TextView) itemView.findViewById(R.id.name);
            mTVSex = (TextView) itemView.findViewById(R.id.sex);
            mTVBirth = (TextView) itemView.findViewById(R.id.date);
            mTVPhone = (TextView) itemView.findViewById(R.id.phone);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.show_employees);
//            linearLayout.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Handling for background selection state changed
                    int previousSelectState = mSelectedItemPosition;
                    mSelectedItemPosition = getAdapterPosition();
                    //notify previous selected item
                    notifyItemChanged(previousSelectState);
                    notifyItemChanged(mSelectedItemPosition);
                    if (clickListener != null)
                        clickListener.onClick(view, getAdapterPosition());
                }
            });
        }
//      lấy
//        @Override
//        public void onClick(View view) {
//            if (clickListener != null)
//                clickListener.onClick(view, getAdapterPosition());
//        }

        public void bindDataWithViewHolder(EmployeesData dataItem, int currentPosition) {
            this.mDataItem = dataItem;
            //Handle selection  state in object View.
            if (currentPosition == mSelectedItemPosition) {
                linearLayout.setBackgroundResource(R.color.green);
            } else {
                linearLayout.setBackgroundResource(R.color.white);
            }
        }
    }
}
