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

public class MapActivityList extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    MapCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        lv = (ListView) findViewById(R.id.list_view_map_main);
        sQLiteHelper = new SQLiteHelper(MapActivityList.this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                final MapModel p = (MapModel) o;
                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivityList.this);
                builder.setTitle("Delete?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                sQLiteHelper.deleteRecordMap(p);
                                getAllRecordsAndSetTOAdapter();
                                dialog.cancel();
                            }
                        });
                builder.setNegativeButton(
                        "No",
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
        ArrayList<MapModel> maps = sQLiteHelper.getAllRecordsMap();
        adapter = new MapCustomAdapter(this, maps , sQLiteHelper,true);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllRecordsAndSetTOAdapter();
    }

    public void buttonmapactivity(View V) {
        Intent intent = new Intent(this, MappingActivity.class);
        startActivity(intent);
    }
}
