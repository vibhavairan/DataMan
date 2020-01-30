package com.vibhav.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class StudentMainActivity extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    StudentCustomAdapter adapter;
    boolean z;
    EditText inputSearch;
    ClassModel classtemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        classtemp = (ClassModel) getIntent().getSerializableExtra("Modify");
        lv = (ListView) findViewById(R.id.list_view_student);
        inputSearch = (EditText) findViewById(R.id.inputSearchStudent);
        sQLiteHelper = new SQLiteHelper(StudentMainActivity.this);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                final StudentModel p = (StudentModel) o;
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentMainActivity.this);
                builder.setMessage("Student ID: "+p.getsID()+"\n"+"Student Name: "+p.getStudentName()+"\n"+"Class ID: "+p.getcID());
                builder.setTitle("Modify or Delete?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                sQLiteHelper.deleteRecordStudent(p);
                                getAllRecordsAndSetTOAdapter();
                                Toast.makeText(StudentMainActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "Modify",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                Intent intent = new Intent(StudentMainActivity.this,AddStudent.class);
                                intent.putExtra("Modify", p);
                                z = false;
                                intent.putExtra("Flag",z);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    private void getAllRecordsAndSetTOAdapter() {
        ArrayList<StudentModel> students = sQLiteHelper.getAllRecordsStudents(classtemp);
        adapter = new StudentCustomAdapter(this, students );
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllRecordsAndSetTOAdapter();
    }
    public void b5(View V)
    {
        Intent intent = new Intent(this,AddStudent.class);
        z = true;
        intent.putExtra("Modify", classtemp);
        intent.putExtra("Flag",z);
        startActivity(intent);
    }

}
