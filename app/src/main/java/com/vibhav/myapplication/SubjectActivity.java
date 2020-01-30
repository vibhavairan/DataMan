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

public class SubjectActivity extends AppCompatActivity {

    private ListView lv;
    SQLiteHelper sQLiteHelper;
    SubjectCustomAdapter adapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        lv = (ListView) findViewById(R.id.list_view_subject);
        inputSearch = (EditText) findViewById(R.id.inputSearchSubject);
        sQLiteHelper = new SQLiteHelper(SubjectActivity.this);
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
                final SubjectModel p = (SubjectModel) o;
                AlertDialog.Builder builder = new AlertDialog.Builder(SubjectActivity.this);
                builder.setMessage("Do you want to Delete it?");
                builder.setTitle("Delete");
                builder.setCancelable(false);
                builder.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                sQLiteHelper.deleteRecordSubject(p);
                                getAllRecordsAndSetTOAdapter();
                                Toast.makeText(SubjectActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void getAllRecordsAndSetTOAdapter() {
        ArrayList<SubjectModel> subjects = sQLiteHelper.getAllRecordsSubject();
        adapter = new SubjectCustomAdapter(this, subjects );
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllRecordsAndSetTOAdapter();
    }
    public void b1(View V)
    {
        Intent intent = new Intent(this, AddSubject.class);
        startActivity(intent);
    }
}
