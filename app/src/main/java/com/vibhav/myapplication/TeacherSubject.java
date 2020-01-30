package com.vibhav.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class TeacherSubject extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    TeacherSubjectCustomAdapter adapter;
    EditText inputSearch;
    TeacherModel teach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subject);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        teach = (TeacherModel) getIntent().getSerializableExtra("Map");
        lv = (ListView) findViewById(R.id.list_view_teacher_subject);
        inputSearch = (EditText) findViewById(R.id.inputSearchSubjectTeacher);
        sQLiteHelper = new SQLiteHelper(TeacherSubject.this);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //DeleteClass.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }
    private void getAllRecordsAndSetTOAdapter() {
        ArrayList<SubjectModel> subjects = sQLiteHelper.getAllRecordsSubject();
        adapter = new TeacherSubjectCustomAdapter(this, subjects,sQLiteHelper,teach);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getAllRecordsAndSetTOAdapter();
    }
    public void buttonnew(View V)
    {
        sQLiteHelper.deleteSubjectTeacher(teach);
        for(int i = 0; i < lv.getCount(); i++)
        {
            SubjectModel temp = (SubjectModel) lv.getItemAtPosition(i);
            if(temp.isMap())
            {
                TeacherSubjectModel t = new TeacherSubjectModel();
                t.setTeacherID(teach.getTeacherID());
                t.setSubjectID(temp.getsubID());
                sQLiteHelper.insertRecordTeacherSubject(t);
            }
        }
        finish();
    }
}
