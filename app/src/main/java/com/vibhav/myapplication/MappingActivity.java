package com.vibhav.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MappingActivity extends AppCompatActivity {
    private SQLiteHelper sQLiteHelper;
    ClassCustomAdapter adapter_class;
    SubjectCustomAdapter adapter_subject;
    TeacherCustomAdapter adapter_teacher;
    Spinner spinner_class, spinner_subject, spinner_teacher;
    ClassModel c;
    SubjectModel s;
    TeacherModel t;
    MapModel m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        sQLiteHelper = new SQLiteHelper(MappingActivity.this);
        m = new MapModel();
        spinner_class = (Spinner) findViewById(R.id.spinner_class);
        spinner_teacher = (Spinner) findViewById(R.id.spinner_teach);
        spinner_subject = (Spinner) findViewById(R.id.spinner_sub);
        addItemsOnSpinnerClass();
        addItemsOnSpinnerTeacher();
        spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object o = spinner_class.getItemAtPosition(position);
                c = (ClassModel) o;
                m.setcID(c.getcID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object o = spinner_teacher.getItemAtPosition(position);
                t = (TeacherModel) o;
                m.settID(t.getTeacherID());
                addItemsOnSpinnerSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object o = spinner_subject.getItemAtPosition(position);
                s= (SubjectModel) o;
                m.setSubID(s.getsubID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addItemsOnSpinnerClass() {
        ArrayList<ClassModel> classes = sQLiteHelper.getAllRecordsClass();
        adapter_class = new ClassCustomAdapter(this, classes, true);
        spinner_class.setAdapter(adapter_class);
    }


    private void addItemsOnSpinnerTeacher() {
        ArrayList<TeacherModel> teachers = sQLiteHelper.getAllRecordsTeacher();
        adapter_teacher = new TeacherCustomAdapter(this, teachers);
        spinner_teacher.setAdapter(adapter_teacher);
    }

    private void addItemsOnSpinnerSubject() {
        ArrayList<SubjectModel> subjects = sQLiteHelper.getAllRecordsSubjectTeacher(m);
        adapter_subject = new SubjectCustomAdapter(this, subjects);
        spinner_subject.setAdapter(adapter_subject);
    }

    public void map(View v) {
        if((!m.getcID().equals("")) && (!m.getSubID().equals("")) && (!m.gettID().equals(""))) {
            if (sQLiteHelper.checkmaprecords(m)) {
                sQLiteHelper.maprecords(m);
                sQLiteHelper.insertRecordAttendance(m);
                Toast.makeText(MappingActivity.this, "Mapped", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Toast.makeText(MappingActivity.this, "Duplicate Record", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(MappingActivity.this, "All fields are compulsory", Toast.LENGTH_SHORT).show();
    }

}
