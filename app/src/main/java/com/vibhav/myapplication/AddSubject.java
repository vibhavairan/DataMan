package com.vibhav.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddSubject extends AppCompatActivity {
    EditText subjectName;
    SQLiteHelper sQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
    }
    public void button(View V) {
        sQLiteHelper = new SQLiteHelper(AddSubject.this);
        subjectName = (EditText)findViewById(R.id.subname);
        String subname = subjectName.getText().toString();
        SubjectModel newSubject = new SubjectModel();
        newSubject.setSubjectName(subname);
        sQLiteHelper.insertRecordSubject(newSubject);
        Toast.makeText(AddSubject.this,"Success", Toast.LENGTH_SHORT).show();
        finish();
    }
}
