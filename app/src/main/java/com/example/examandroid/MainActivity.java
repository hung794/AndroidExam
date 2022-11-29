package com.example.examandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examandroid.database.DbHelper;
import com.example.examandroid.entity.Employee;
import com.example.examandroid.entity.ListEmployeeAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final DbHelper dbHelper = new DbHelper(this);
    private EditText name;
    private EditText designation;
    private EditText salary;
    private Button add;
    private Button delete;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Employee> list = dbHelper.getAllEmployee();
        ListEmployeeAdapter employeeAdapter = new ListEmployeeAdapter(this, list);
        ListView listView = findViewById(R.id.listEmployee);
        listView.setAdapter(employeeAdapter);
        name = findViewById(R.id.name);
        designation = findViewById(R.id.designation);
        salary = findViewById(R.id.salary);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                String regex = "-?\\d+(.\\d+)?";
                if (name.getText().toString().trim().isEmpty() || designation.getText().toString().trim().isEmpty() || salary.getText().toString().trim().isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "You must enter the full employee name, designation and salary!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                else if(!salary.getText().toString().matches(regex)){
                    Toast toast = Toast.makeText(MainActivity.this, "Salary must be number!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0,0);
                    toast.show();
                }
                else {
                    String employeeName = name.getText().toString().trim();
                    String employeeDesignation = designation.getText().toString().trim();
                    String employeeSalary = salary.getText().toString().trim();
                    Employee employee = new Employee(employeeName, employeeDesignation, Double.parseDouble(employeeSalary));
                    dbHelper.addEmployee(employee);
                    List<Employee> list = dbHelper.getAllEmployee();
                    ListEmployeeAdapter employeeAdapter = new ListEmployeeAdapter(this, list);
                    ListView listView = findViewById(R.id.listEmployee);
                    listView.setAdapter(employeeAdapter);
                    Toast.makeText(this, "Add employee success!", Toast.LENGTH_LONG).show();
                    name.setText(null);
                    designation.setText(null);
                    salary.setText(null);
                }
                break;
            default:
                break;
        }
    }
}