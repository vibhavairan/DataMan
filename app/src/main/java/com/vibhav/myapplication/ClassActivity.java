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

public class ClassActivity extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    ClassCustomAdapter adapter;
    EditText inputSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        sQLiteHelper = new SQLiteHelper(ClassActivity.this);
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
                final ClassModel p = (ClassModel)o;
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassActivity.this);
                builder.setMessage("Class ID: "+p.getcID()+"\n"+"Class Name: "+p.getClassName()+"\n"+"Max Students: "+p.getMaxStu());
                builder.setTitle("Do you want to Modify it or Delete it?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                sQLiteHelper.deleteRecordClass(p);
                                getAllRecordsAndSetTOAdapter();
                                Toast.makeText(ClassActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
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
                                dialog.cancel();
                                Intent intent = new Intent(ClassActivity.this,ModifyActivity.class);
                                intent.putExtra("Modify", p);
                                startActivity(intent);
                            }
                        });
                builder.setNeutralButton(
                        "Choose",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                dialog.cancel();
                                Intent intent = new Intent(ClassActivity.this,StudentMainActivity.class);
                                intent.putExtra("Modify", p);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void getAllRecordsAndSetTOAdapter() {
        ArrayList<ClassModel> classes = sQLiteHelper.getAllRecordsClass();
        adapter = new ClassCustomAdapter(this, classes ,false);
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
        Intent intent = new Intent(this, AddClass.class);
        startActivity(intent);
    }
}
