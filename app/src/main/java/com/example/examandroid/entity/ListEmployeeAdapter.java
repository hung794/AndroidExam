package com.example.examandroid.entity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.examandroid.R;

import java.text.DecimalFormat;
import java.util.List;

public class ListEmployeeAdapter extends BaseAdapter {
    private Activity activity;
    private List<Employee> employeeList;

    public ListEmployeeAdapter(Activity activity, List<Employee> employeeList) {
        this.activity = activity;
        this.employeeList = employeeList;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        view = layoutInflater.inflate(R.layout.employee_item,
                parent, false);
        Employee employee = employeeList.get(position);
        TextView tvName = view.findViewById(R.id.name);
        TextView tvDesignation = view.findViewById(R.id.designation);
        TextView tvSalary = view.findViewById(R.id.salary);
        tvName.setText(employee.getName());
        tvDesignation.setText(employee.getDesignation());
        String salary = new DecimalFormat("#,###.00").format(employee.getSalary());
        tvSalary.setText(salary);
        return view;
    }


}
