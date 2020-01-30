package com.vibhav.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddClass extends AppCompatActivity {
    EditText cName;
    EditText maxStu;
    SQLiteHelper sQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
    }

    public void button(View V) {
        sQLiteHelper = new SQLiteHelper(AddClass.this);
        cName = (EditText)findViewById(R.id.cname);
        maxStu = (EditText)findViewById(R.id.maxstu);
          String cname = cName.getText().toString();
          String maxstu = maxStu.getText().toString();
            ClassModel newClass = new ClassModel();
            newClass.setClassName(cname);
            newClass.setMaxStu(maxstu);
            sQLiteHelper.insertRecordClass(newClass);
        Toast.makeText(AddClass.this,"Success", Toast.LENGTH_SHORT).show();
        finish();
    }
}