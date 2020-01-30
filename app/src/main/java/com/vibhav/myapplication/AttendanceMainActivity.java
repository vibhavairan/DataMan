package com.vibhav.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class AttendanceMainActivity extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    AttendanceCustomAdapter adapter;
    EditText inputSearch;
    MapModel tem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_main);
        tem = (MapModel) getIntent().getSerializableExtra("Map");
        lv = (ListView) findViewById(R.id.list_view_attendance);
        inputSearch = (EditText) findViewById(R.id.inputSearchAttendance);
        sQLiteHelper = new SQLiteHelper(AttendanceMainActivity.this);
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
        ArrayList<AttendanceModel> students = sQLiteHelper.getAllRecordsStudentsAttendance(tem);
        adapter = new AttendanceCustomAdapter(this, students ,sQLiteHelper,true);
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
        for(int i = 0; i < lv.getCount(); i++)
        {
            AttendanceModel temp = (AttendanceModel) lv.getItemAtPosition(i);
            sQLiteHelper.updateAttendance(temp);
            sQLiteHelper.updateDate(tem);
        }
        finish();
    }

}
