package com.example.ominext.quanlynhansu.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Ominext on 8/8/2017.
 */

public class DatePickerFrm extends android.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Context context;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    public DatePickerFrm(){

    }
    public DatePickerFrm(DatePickerDialog.OnDateSetListener onDateSetListener,Context context){
        this.context=context;
        this.onDateSetListener=onDateSetListener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    //  Set ngày từ bên ngoài vào
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
    }

}
