package com.example.ominext.quanlynhansu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ominext.quanlynhansu.R;
import com.example.ominext.quanlynhansu.adapter.RecyclerViewAdapterFFrm1;
import com.example.ominext.quanlynhansu.listener.OnItemClickListener;
import com.example.ominext.quanlynhansu.model.EmployeesData;
import com.example.ominext.quanlynhansu.model.JSNWriter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import helper.FileHelper;

public class Fragment1 extends Fragment implements OnItemClickListener {

    @BindView(R.id.rv_listEmployees)
    RecyclerView rvListEmployees;
    @BindView(R.id.bt_add)
    Button btAdd;
    Unbinder unbinder;

    RecyclerView.LayoutManager layoutManager;
    //dataList bị trống
    //Lấy dữ liệu từ json lên
    List<EmployeesData> dataList = new ArrayList<>();
    RecyclerViewAdapterFFrm1 adapter;
    @BindView(R.id.frame_add_employees)
    FrameLayout frameAddEmployees;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.bt_edit)
    Button btEdit;
    int position = -1;
    File file;
    String mAppDir = null;

    private final String fileName = "nhanvien1.txt";
    private final String fileName1 = "nhanvien2.txt";

    public int flag=0;
    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    //1
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //2
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetData();
        layoutManager = new GridLayoutManager(getContext(), 1);
        rvListEmployees.setLayoutManager(layoutManager);
        rvListEmployees.setHasFixedSize(true);
        adapter = new RecyclerViewAdapterFFrm1(dataList, getContext());
        rvListEmployees.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    public void GetData() {
//      lấy dữ liệu từ file text ra, chuyển chuỗi json thành đối tượng add vào datalist đẩy lên listview
        try {
            init();
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
                String sexE = employeeObj.getString("sex");
                String birthDayE = employeeObj.getString("birth");
                String phoneE = employeeObj.getString("phone");

                employeesData1.setmId(Integer.parseInt(idE));
                employeesData1.setmName(nameE);
                employeesData1.setmSex(sexE);
                employeesData1.setmDateOfBirth(birthDayE);
                employeesData1.setmPhone(phoneE);
                dataList.add(employeesData1);

            }
            adapter.notifyDataSetChanged();
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_add)
    public void onViewClicked() {
        Fragment fragment = new AddEmployees();
        Bundle bundle = new Bundle();
        bundle.putString("id", "");
        bundle.putString("name", "");
        bundle.putString("dateOfBirth", "");
        bundle.putString("sex", "");
        bundle.putString("phone", "");
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_add_employees, fragment);
        transaction.commit();
    }

    public void init() {
        file = new File(Environment.getExternalStorageDirectory(), getContext().getPackageName());
        if (!file.exists())
            file.mkdir();
        mAppDir = file.getAbsolutePath() + "/";
    }

    @OnClick({R.id.bt_delete, R.id.bt_edit})
    public void onViewClicked(View view) {
        EmployeesData employee = null;
        //tạo 1 ds xóa
        if ((position > -1) && (dataList.size() > 0) && (position <= dataList.size())) {
            init();
            employee = new EmployeesData();
            employee.setmId(dataList.get(position).getmId());
            employee.setmName(dataList.get(position).getmName());
            employee.setmSex(dataList.get(position).getmSex());
            employee.setmDateOfBirth(dataList.get(position).getmDateOfBirth());
            employee.setmPhone(dataList.get(position).getmPhone());
        }
        switch (view.getId()) {
            case R.id.bt_delete:
                if ((position > -1) && (dataList.size() > 0) && (position <= dataList.size())) {
                    //có vị trí -> tìm data ở hàng thứ i -> xóa data đó đi
                    try {
                        StringWriter output = new StringWriter();
                        JSNWriter.writeJsonStream(output, employee);
                        String jsonText = output.toString();
                        FileHelper.deleteAnEmployee(mAppDir, fileName, fileName1, jsonText);
                        dataList.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        position = -1;
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu xóa", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.bt_edit:

                if ((position > -1) && (dataList.size() > 0) && (position <= dataList.size())) {
                    //có vị trí -> tìm data ở hàng thứ i -> xóa data đó đi
                    try {
                        StringWriter output = new StringWriter();
                        JSNWriter.writeJsonStream(output, employee);
                        String jsonText = output.toString();
                        FileHelper.deleteAnEmployee(mAppDir, fileName, fileName1, jsonText);
                        dataList.remove(position);
                        position = -1;
                    } catch (IOException e) {
                    }
                    Fragment fragment = new AddEmployees();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(employee.getmId()));
                    bundle.putString("name", employee.getmName());
                    bundle.putString("dateOfBirth", employee.getmDateOfBirth());
                    bundle.putString("sex", employee.getmSex());
                    bundle.putString("phone", employee.getmPhone());
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.frame_add_employees, fragment);
                    transaction.commit();
                }
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {
        this.position = position;
    }
}