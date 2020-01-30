package com.vibhav.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TeacherMainActivity extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    MapCustomAdapter adapter;
    TeacherModel temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        //Toolbar toolbar = findViewById(R.id.tool_bar);
        ImageView info = findViewById(R.id.info);
        temp = (TeacherModel) getIntent().getSerializableExtra("Map");
        lv = (ListView) findViewById(R.id.list_view_map);
        TextView tv;
        tv = findViewById(R.id.title);
        sQLiteHelper = new SQLiteHelper(TeacherMainActivity.this);
        tv.setText(sQLiteHelper.getTeacherName2(temp));
        info.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        TeacherModel t = sQLiteHelper.getSingleRecordsTeacher(temp);
                                        final Dialog dialog = new Dialog(TeacherMainActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(true);
                                        dialog.setContentView(R.layout.dialog_teacher_details);
                                        ImageView img = (ImageView) dialog.findViewById(R.id.ivTeacher);
                                        TextView id = (TextView) dialog.findViewById(R.id.tvID);
                                        TextView name = (TextView) dialog.findViewById(R.id.tvName);
                                        TextView gender = (TextView) dialog.findViewById(R.id.tvGender);
                                        TextView dob = (TextView) dialog.findViewById(R.id.tvDob);
                                        TextView address = (TextView) dialog.findViewById(R.id.tvAdd);
                                        TextView mail = (TextView) dialog.findViewById(R.id.tvmail);
                                        TextView number = (TextView) dialog.findViewById(R.id.tvno);
                                        TextView pass = (TextView) dialog.findViewById(R.id.tvpass);
                                        String ID, NAME, GENDER, DOB, ADD, MAIL, NUM, PASS;
                                        byte[] data = t.getTeachphoto();
                                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        img.setImageBitmap(bmp);
                                        ID = "ID: "+t.getTeacherID();
                                        NAME = "Name: "+t.getTeachName();
                                        GENDER = "Gender: "+t.getTeachgender();
                                        DOB = "Dob: "+t.getTeachdob();
                                        ADD = "Address: "+t.getTeachadd();
                                        MAIL = "Mail: "+t.getTeachmail();
                                        NUM = "Number: "+t.getTeachnum();
                                        PASS = "Password: "+t.getTeachpass();
                                        id.setText(ID);
                                        name.setText(NAME);
                                        gender.setText(GENDER);
                                        dob.setText(DOB);
                                        address.setText(ADD);
                                        mail.setText(MAIL);
                                        number.setText(NUM);
                                        pass.setText(PASS);
                                        Button dialogButton = (Button) dialog.findViewById(R.id.butdiss);
                                        dialogButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }
                                }
        );
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                final MapModel p = (MapModel) o;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Calendar c = Calendar.getInstance();
                final String date = sdf.format(c.getTime());
               // if (!p.getDate().equals(date)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TeacherMainActivity.this);
                    builder.setTitle("Do you want take Attendance?");
                    builder.setCancelable(true);
                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    p.setDate(date);
                                    Intent intent = new Intent(TeacherMainActivity.this, AttendanceMainActivity.class);
                                    intent.putExtra("Map", p);
                                    startActivity(intent);
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
                /*} else {
                    Toast.makeText(TeacherMainActivity.this, "You have already taken the Attendance", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void getAllRecordsAndSetTOAdapter() {
        ArrayList<MapModel> records = sQLiteHelper.getAllRecordsTeacherMap(temp);
        adapter = new MapCustomAdapter(this, records, sQLiteHelper, false);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllRecordsAndSetTOAdapter();
    }
}
