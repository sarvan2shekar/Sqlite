package com.testapp.sarvan.sqlite.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.testapp.sarvan.sqlite.R;
import com.testapp.sarvan.sqlite.model.Employee;
import com.testapp.sarvan.sqlite.presenter.DB.EmployeeOperations;

import java.util.List;

public class ViewAllEmployees extends AppCompatActivity {

    List<Employee> employees;
    private EmployeeOperations employeeOps;

    @Override
    public String toString() {
        return "ViewAllEmployees{" +
                "employees=" + employees +
                '}';
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_employees);
        employeeOps = new EmployeeOperations(this);
        employeeOps.open();
        employees = employeeOps.getAllEmployees();
        employeeOps.close();

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        MyAdapter myAdapter = new MyAdapter(employees);
        rv.setAdapter(myAdapter);
//        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, employees);
        //setListAdapter(adapter);
    }
}