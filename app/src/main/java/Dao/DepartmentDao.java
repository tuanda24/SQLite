package Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
//import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import Enitity.Department;
import Dao.DepartmentDao;
import Utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import Enitity.Department;

public class DepartmentDao {

    private static String TAG = "DepartmentDao";

    private DatabaseUtils databaseUtils;

    public DepartmentDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        SQLiteDatabase database = databaseUtils.getReadableDatabase();

        String sql = "SELECT id, name FROM Department";
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int departmentId = cursor.getInt(0);
            String departmentName = cursor.getString(1);
            departments.add(new Department(departmentId, departmentName));
            cursor.moveToNext();
        }

        cursor.close();
        return departments;
    }

    public Department getDepartmentByID(int id) {
        Cursor cursor=null;
        try{
            SQLiteDatabase database = databaseUtils.getReadableDatabase();
            String sql = "SELECT id, name FROM Department WHERE id = ?";
            String[] params = new String[]{id + ""};
            cursor = database.rawQuery(sql, params);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int departmentId = cursor.getInt(0);
                String departmentName = cursor.getString(1);
                Department department = new Department(departmentId, departmentName);
                return department;
            }
        }catch (Exception e){
            Log.d(TAG,"getDepartmentByID:"+e.toString());
        }finally {
            cursor.close();

        }
        return  null;
    }

    public void createDepartment(String name) {
        SQLiteDatabase database = databaseUtils.getWritableDatabase();

        // values
        ContentValues values = new ContentValues();
        values.put("name", name);
        //nếu ko tự tưng thì phải đưa data cho id
//        values.put("id",1);
        //1. tên baảng, 3: value
        long departmentId = database.insert(
                "Department",
                null,
                values);
        Log.i(TAG, "Newly Department ID " + departmentId);
    }

    public void updateDepartmentByID(int id, String newName) {
        SQLiteDatabase database = databaseUtils.getWritableDatabase();

        // set
        ContentValues values = new ContentValues();
        values.put("name", newName);
        // where
        String whereClause = "id=? ,";
        String[] whereParams = {id + ""};
        //1. tên bảng, 2.value, 3 whre, 1: where value
        int affectedRecordAmount = database.update(
                "Department",
                values,
                whereClause,
                whereParams);
        Log.i(TAG, "Affected Record Amount: " + affectedRecordAmount);
    }

    public void deleteDepartmentByID(int id) {
        SQLiteDatabase database = databaseUtils.getWritableDatabase();

        // where
        String whereClause = "id=?, name=?";
        String[] whereParams = {id + "","1"};

        int affectedRecordAmount = database.delete(
                "Department",
                whereClause,
                whereParams);
        Log.i(TAG, "Affected Record Amount: " + affectedRecordAmount);
    }

    public void close(){
        databaseUtils.close();
    }

}
