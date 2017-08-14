package com.example.ominext.quanlynhansu.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.quanlynhansu.R;
import com.example.ominext.quanlynhansu.adapter.RecyclerViewAdapterFFrm2;
import com.example.ominext.quanlynhansu.model.EmployeesData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import helper.LogUtils;

public class Fragment2 extends Fragment {
    @BindView(R.id.age_start)
    EditText ageStart;
    @BindView(R.id.age_final)
    EditText ageFinal;
    @BindView(R.id.bt_total)
    Button btTotal;
    @BindView(R.id.rv_total)
    RecyclerView rvTotal;
    Unbinder unbinder;

    List<EmployeesData> dataList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapterFFrm2 adapter;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dateOfBirth)
    TextView dateOfBirth;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    int startAge;
    int finalAge;

    public Fragment2() {
        // Required empty public constructor
    }

    //  khi ấn vào nút thống kê thì kết quả trả ra sẽ là danh sách các nhân viên có độ tuổi trong khoảng từ. vd từ 22 đến 40 tuổi
//    tạo 1 list chứa danh sách có trong file text
//    ràng buộc điều kiện cho list đó: lấy ngày hiện tại trừ đi ngày của phần tử trong list xem có thuộc khoảng từ 22-40 không nếu đúng thì add vào RecyclerView
//
    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new GridLayoutManager(getContext(), 1);
        rvTotal.setLayoutManager(layoutManager);
        rvTotal.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_total)
    public void onViewClicked() {
        dataList.clear();
        try {
            File file = new File(Environment.getExternalStorageDirectory(), getContext().getPackageName());
            if (!file.exists())
                file.mkdir();
            String mAppDir = file.getAbsolutePath();

            file = new File(mAppDir, "nhanvien1.txt");
            if (!file.exists())
                throw new FileNotFoundException();

            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String s;
            while ((s = reader.readLine()) != null) {
                JSONObject employeeObj = new JSONObject(s);
                EmployeesData employeesData1 = new EmployeesData();

                String idE = employeeObj.getString("id");
                String nameE = employeeObj.getString("name");
//            LogUtils.e("++++++++++++" + nameE);
                String sexE = employeeObj.getString("sex");
                String birthDayE = employeeObj.getString("birth");
                String phoneE = employeeObj.getString("phone");

                employeesData1.setmId(Integer.parseInt(idE));
                employeesData1.setmName(nameE);
                employeesData1.setmSex(sexE);
                employeesData1.setmDateOfBirth(birthDayE);
                employeesData1.setmPhone(phoneE);
                //chuyển từ string sang kiểu định dạng ngày tháng năm
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDay = df.parse(birthDayE);
                String yearBirthDay = (String) android.text.format.DateFormat.format("yyyy", birthDay);
                String monthBirthDay = (String) android.text.format.DateFormat.format("MM", birthDay);

                Calendar calendar = Calendar.getInstance();
                int yearCurrent = calendar.get(Calendar.YEAR);
                int monthCurrent = calendar.get(Calendar.MONTH);
                int monthTotal = (yearCurrent * 12 + monthCurrent) - (Integer.parseInt(yearBirthDay) * 12 + Integer.parseInt(monthBirthDay));
                int age = monthTotal / 12;
                LogUtils.e("++++++++++++" + age);
                startAge = Integer.parseInt(ageStart.getText().toString());
                finalAge = Integer.parseInt(ageFinal.getText().toString());
                if ((age >= startAge) && (age <= finalAge)) dataList.add(employeesData1);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT);
        }
        if (dataList.size() == 0) {
            Toast.makeText(getContext(), "Không có nhân viên nào thuộc giới hạn tuổi trên", Toast.LENGTH_SHORT).show();
        }
        adapter = new RecyclerViewAdapterFFrm2(dataList, getContext());
        rvTotal.setAdapter(adapter);
    }
}
