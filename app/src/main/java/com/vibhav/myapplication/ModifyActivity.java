package com.vibhav.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {
    EditText cNamemod = null;
    EditText maxStumod = null;
    SQLiteHelper sQLiteHelper;
    ClassModel p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
    }
    public void button(View V) {
        sQLiteHelper = new SQLiteHelper(ModifyActivity.this);
        cNamemod = (EditText)findViewById(R.id.cnamemod);
        maxStumod = (EditText)findViewById(R.id.maxstumod);
        String cname = cNamemod.getText().toString();
        String maxstu = maxStumod.getText().toString();
        p = (ClassModel) getIntent().getSerializableExtra("Modify");
        if(!cname.equals(""))
        p.setClassName(cname);
        if(!maxstu.equals(""))
        p.setMaxStu(maxstu);
        sQLiteHelper.updateRecordClass(p);
        Toast.makeText(ModifyActivity.this,"Modified", Toast.LENGTH_SHORT).show();
        finish();
    }
}
