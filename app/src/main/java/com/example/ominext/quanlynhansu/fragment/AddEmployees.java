package com.example.ominext.quanlynhansu.fragment;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.quanlynhansu.R;
import com.example.ominext.quanlynhansu.model.EmployeesData;
import com.example.ominext.quanlynhansu.model.JSNWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import helper.FileHelper;

public class AddEmployees extends Fragment {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.cb_man)
    CheckBox cbMan;
    @BindView(R.id.cb_woman)
    CheckBox cbWoman;
    @BindView(R.id.edt_dateOfBirth)
    TextView edtDateOfBirth;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.esc)
    Button esc;
    Unbinder unbinder;
    String mAppDir = null;
    @BindView(R.id.edt_id)
    EditText edtId;
    @BindView(R.id.img_calendar)
    ImageView imgCalendar;
    private File file;
    private final String fileName = "nhanvien1.txt";

    public AddEmployees() {
        // Required empty public constructor
    }

    public static AddEmployees newInstance() {
        AddEmployees fragment = new AddEmployees();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        int yearCurrent = calendar.get(Calendar.YEAR);
        int monthCurrent = calendar.get(Calendar.MONTH);
        int dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        edtDateOfBirth.setText(dayCurrent + "/" + monthCurrent + "/" + yearCurrent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_employees, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    public void init() {
        file = new File(Environment.getExternalStorageDirectory(), getContext().getPackageName());
        if (!file.exists())
            file.mkdir();
        mAppDir = file.getAbsolutePath() + "/";
    }

    @OnClick({R.id.cb_man, R.id.cb_woman, R.id.save, R.id.esc})
    public void onViewClicked(View view) {
        EmployeesData employee = new EmployeesData();
        switch (view.getId()) {
            case R.id.cb_man:
                if (cbWoman.isChecked()) {
                    cbWoman.setChecked(false);
                }
                break;
            case R.id.cb_woman:
                if (cbMan.isChecked()) {
                    cbMan.setChecked(false);
                }
                break;
            case R.id.save:
//              1 nhân viên kiểu employee
//                EmployeesData employee = new EmployeesData();
                String id = edtId.getText().toString();
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String birthDay = edtDateOfBirth.getText().toString();
                if ((!id.equals("")) && (!name.equals("")) && (!phone.equals("")) && (!birthDay.equals("")) && (cbMan.isChecked() || cbWoman.isChecked())) {
                    employee.setmId(Integer.parseInt(edtId.getText().toString()));
                    employee.setmName(edtName.getText().toString());
                    if (cbMan.isChecked()) {
                        employee.setmSex("Nam");
                    }
                    if (cbWoman.isChecked()) {
                        employee.setmSex("Nữ");
                    }
                    employee.setmDateOfBirth(edtDateOfBirth.getText().toString());
                    employee.setmPhone(edtPhone.getText().toString());
                    init();
                    //lưu vào trong file text: 1- có một file text từ trước; 2- Load dữ liệu từ file text đó lên; 3- Ghép dữ liệu từ file text đó với dữ liệu mới; 4- Lưu vào file text
                    try {
                        StringWriter output = new StringWriter();
                        JSNWriter.writeJsonStream(output, employee);
                        String jSonText = output.toString();
                        FileHelper.saveToFile(mAppDir, fileName, jSonText);
                        Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.esc:
                edtId.setText("");
                edtDateOfBirth.setText("");
                edtName.setText("");
                edtPhone.setText("");
                cbWoman.setChecked(false);
                cbMan.setChecked(false);
                Toast.makeText(getContext(), "Hủy xong", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //    khi kích vào img calendar thì hiển thị lên 1 dialog nd là 1 datpicker
    @OnClick(R.id.img_calendar)
    public void onViewClicked() {
        android.app.DialogFragment newFragment;
//        newFragment.show(getActivity().getFragmentManager(),"datePicker");
//        datePicker=newFragment;
        newFragment = new DatePickerDialogFragment(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = GregorianCalendar.getInstance();
                cal.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateandTime = formatter.format(cal.getTime());
                edtDateOfBirth.setText(currentDateandTime);
            }
        }, getContext());
        newFragment.show(getActivity().getFragmentManager(), "Date");
    }
}
