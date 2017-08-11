package com.example.ominext.quanlynhansu.listener;

import android.view.View;

import com.example.ominext.quanlynhansu.model.EmployeesData;

/**
 * Created by Ominext on 8/11/2017.
 */

public interface OnItemClickListener {
    public void onClick(View view, int position, EmployeesData data);
//    public void OnDoubleClick(View view,int position);
}
