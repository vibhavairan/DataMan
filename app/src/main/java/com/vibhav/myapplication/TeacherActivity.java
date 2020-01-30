package com.vibhav.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class TeacherActivity extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    TeacherCustomAdapter adapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        lv = (ListView) findViewById(R.id.list_view_teacher);
        inputSearch = (EditText) findViewById(R.id.inputSearchTeacher);
        sQLiteHelper = new SQLiteHelper(TeacherActivity.this);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);            }

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
                final TeacherModel p = (TeacherModel) o;
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherActivity.this);
                builder.setMessage("Teacher ID: "+p.getTeacherID()+"\nPassword:"+p.getTeachpass());
                builder.setTitle("Options");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Change Password",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(TeacherActivity.this);
                                builder2.setMessage("Enter New Password");
                                builder2.setTitle("Change Password");
                                builder2.setCancelable(true);
                                View viewInflated = LayoutInflater.from(TeacherActivity.this).inflate(R.layout.teacher_pass_modify,null);
                                final EditText input = (EditText) viewInflated.findViewById(R.id.mod_pass);
                                final EditText input2 = (EditText) viewInflated.findViewById(R.id.mod_pass2);
                                builder2.setView(viewInflated);
                                builder2.setNegativeButton(
                                        "Confirm",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                if(input.getText().toString().equals(input2.getText().toString()))
                                                {
                                                String new_pass = input.getText().toString();
                                                p.setTeachpass(new_pass);
                                                sQLiteHelper.updateRecordTeacher(p);
                                                getAllRecordsAndSetTOAdapter();
                                                Toast.makeText(TeacherActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                    Toast.makeText(TeacherActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                AlertDialog alertDialog = builder2.create();
                                alertDialog.show();
                            }
                        });
                builder.setNegativeButton(
                        "Choose",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent(TeacherActivity.this,TeacherSubject.class);
                                intent.putExtra("Map", p);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });
                builder.setNeutralButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                sQLiteHelper.deleteRecordTeacher(p);
                                getAllRecordsAndSetTOAdapter();
                                Toast.makeText(TeacherActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void getAllRecordsAndSetTOAdapter() {
        ArrayList<TeacherModel> teachers = sQLiteHelper.getAllRecordsTeacher();
        adapter = new TeacherCustomAdapter(this, teachers);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllRecordsAndSetTOAdapter();
    }

    public void b8(View V) {
        Intent intent = new Intent(this, AddTeacher.class);
        startActivity(intent);
    }
}