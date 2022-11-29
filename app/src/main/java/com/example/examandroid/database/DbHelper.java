package com.example.examandroid.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.examandroid.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "EmployeeDatabase1";
    // Country table name
    private static final String TABLE_EMPLOYEE = "Employees";
    // Country Table Columns names
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESIGNATION = "designation";
    private static final String FIELD_SALARY = "salary";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE
                + " (" + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                       + FIELD_NAME + " TEXT, "
                       + FIELD_DESIGNATION + " TEXT, "
                       + FIELD_SALARY + " DOUBLE)";
        db.execSQL(CREATE_EMPLOYEE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    @SuppressLint("Range")
    public List getAllEmployee(){
        List employeeList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(cursor.getString(0)));
                employee.setName(cursor.getString(cursor.getColumnIndex("name")));
                employee.setDesignation(cursor.getString(cursor.getColumnIndex("designation")));
                employee.setSalary(Double.parseDouble(cursor.getString(cursor.getColumnIndex("salary"))));
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }
        return employeeList;
    }

    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, employee.getName());
        values.put(FIELD_DESIGNATION, employee.getDesignation());
        values.put(FIELD_SALARY, employee.getSalary());
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
    }

    public void deleteAllEmployee() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE,null,null);
        db.close();
    }

    public void deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, FIELD_ID + " = ?",
                new String[] { String.valueOf(employee.getId()) });
        db.close();
    }

    public int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, employee.getName());
        values.put(FIELD_DESIGNATION, employee.getDesignation());
        values.put(FIELD_SALARY, employee.getSalary());

        // updating row
        return db.update(TABLE_EMPLOYEE, values, FIELD_ID + " = ?",
                new String[] { String.valueOf(employee.getId()) });
    }
}
