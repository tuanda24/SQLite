package com.example.bai9_sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import Enitity.Department;
import Dao.DepartmentDao;
import Utils.DatabaseUtils;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private EditText edt_department_id;
    private EditText edt_department_name;
    Button btnXemDS;

    private DepartmentDao departmentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        departmentDao = new DepartmentDao(this);

        edt_department_id = findViewById(R.id.edt_department_id);
        edt_department_name = findViewById(R.id.edt_department_name);
//        Button btnXemDS=findViewById(R.id.btnXemDS);
//        btnXemDS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doGetList();
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_delete) {
            doDelete();
            Toast.makeText(getApplicationContext(),
                    "Item Delete Selected",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id==R.id.action_add) {
            doCreate();
            Toast.makeText(getApplicationContext(),
                    "Item action_add  Selected",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id== R.id.action_update) {
            doUpdate();
            Toast.makeText(getApplicationContext(),"Item update Selected",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id==R.id.action_close) {
            finish();
            return true;
        }
        else if (id==R.id.action_search) {


            Toast.makeText(
                    getApplicationContext(),
                    "Item search Selected",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
    public void doGetList(){
        List<Department> departments = departmentDao.getAllDepartments();
        String names = "";
        for (Department department : departments) {
            names += department.getName() + "\n";
        }
//            Toast.makeText(this, names, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,
                DisplayActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("dsphong",
                (Serializable) departments);// key nhận ds la dsphong
        intent.putExtra("obj",bundle);//tên bundel bên nận obj
        startActivity(intent);

    }
    public void doGetDetail(){
        String mess="";
        try {
            int id = Integer.parseInt(edt_department_id.getText().toString());
            Department department = departmentDao.getDepartmentByID(id);
            mess=department.getName();
        } catch (NumberFormatException e){
            mess="loi: sai du lieu ma phong";
        }catch (NullPointerException n){
            mess="loi: khong thay phong";
        } catch (Exception e){
            mess= " loi" + e.toString();
        }
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }
    public void doCreate(){
        String name = edt_department_name.getText().toString();

        departmentDao.createDepartment(name);
        Toast.makeText(this, "Create Successfully!", Toast.LENGTH_SHORT).show();
    }
    public void doUpdate(){
        int id = Integer.parseInt(edt_department_id.getText().toString());
        String name = edt_department_name.getText().toString();
        departmentDao.updateDepartmentByID(id, name);
        Toast.makeText(this, "Update Successfully!", Toast.LENGTH_SHORT).show();
    }
    public void doDelete(){
        int id = Integer.parseInt(edt_department_id.getText().toString());
        departmentDao.deleteDepartmentByID(id);
        Toast.makeText(this, "Delete Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        departmentDao.close();
        super.onDestroy();
    }
}