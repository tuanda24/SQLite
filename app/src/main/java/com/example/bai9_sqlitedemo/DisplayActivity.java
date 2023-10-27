package com.example.bai9_sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


import Enitity.Department;

public class DisplayActivity extends AppCompatActivity
        implements View.OnClickListener{
    ListView lvDSPhong;
    Button btnTroVe;
    ArrayAdapter<Department> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        //get view from layout
        getWidget();
        btnTroVe.setOnClickListener(this);
        //get data from MainActivity sent
        Intent intent=getIntent();
        ArrayList<Department> departments =new ArrayList<>();
        Bundle bundle= intent.getBundleExtra("obj");
        departments=
                (ArrayList<Department>)
                        bundle.getSerializable("dsphong");
        //set adapter for lv
        adapter=new ArrayAdapter<Department>(this,
                android.R.layout.simple_list_item_1
                ,departments);
        lvDSPhong.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void getWidget(){
        btnTroVe=findViewById(R.id.btnTroVe);
        lvDSPhong=findViewById(R.id.lvDSPhong);
    }

    @Override
    public void onClick(View v) {
        if (v==btnTroVe){
            finish();
        }

    }
}
