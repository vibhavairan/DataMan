package com.vibhav.myapplication;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private ListView lv;
    SQLiteHelper sQLiteHelper;
    AttendanceCustomAdapter adapter;
    StudentModel m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        lv = (ListView) findViewById(R.id.list_view_student_attendance);
        StudentModel s = (StudentModel) getIntent().getSerializableExtra("Map");
        sQLiteHelper = new SQLiteHelper(StudentActivity.this);
        m = sQLiteHelper.getSingleRecordStudent(s);
        TextView tt;
        ImageView info;
        tt = findViewById(R.id.title_student);
        info = findViewById(R.id.info_student);
        tt.setText(m.getStudentName());
        info.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        final Dialog dialog = new Dialog(StudentActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(true);
                                        dialog.setContentView(R.layout.dialog_student_details);
                                        ImageView img = (ImageView) dialog.findViewById(R.id.ivStudent);
                                        TextView id = (TextView) dialog.findViewById(R.id.tvIDStudent);
                                        TextView cid = (TextView) dialog.findViewById(R.id.tvIDClassStudent);
                                        TextView name = (TextView) dialog.findViewById(R.id.tvNameStudent);
                                        TextView gender = (TextView) dialog.findViewById(R.id.tvGenderStudent);
                                        TextView dob = (TextView) dialog.findViewById(R.id.tvDobStudent);
                                        TextView address = (TextView) dialog.findViewById(R.id.tvAddStudent);
                                        TextView mail = (TextView) dialog.findViewById(R.id.tvmailStudent);
                                        TextView number = (TextView) dialog.findViewById(R.id.tvnoStudent);
                                        TextView pass = (TextView) dialog.findViewById(R.id.tvpassStudent);
                                        TextView fname = (TextView) dialog.findViewById(R.id.tvFname);
                                        TextView fno = (TextView) dialog.findViewById(R.id.tvFno);
                                        String ID, CID, NAME, GENDER, DOB, ADD, MAIL, NUM, PASS, FNAME, FNO;
                                        byte[] data = m.getPhoto();
                                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        img.setImageBitmap(bmp);
                                        ID = "ID: "+m.getsID();
                                        CID = "CID: "+m.getcID();
                                        NAME = "Name: "+m.getStudentName();
                                        GENDER = "Gender: "+m.getGender();
                                        DOB = "Dob: "+m.getDob();
                                        ADD = "Address: "+m.getAddress();
                                        MAIL = "Mail: "+m.getMail();
                                        NUM = "Number: "+m.getSno();
                                        PASS = "Password: "+m.getPass();
                                        FNAME = "Father's Name: "+m.getFname();
                                        FNO = "Father's Number: "+m.getFno();
                                        id.setText(ID);
                                        cid.setText(CID);
                                        name.setText(NAME);
                                        gender.setText(GENDER);
                                        dob.setText(DOB);
                                        address.setText(ADD);
                                        mail.setText(MAIL);
                                        number.setText(NUM);
                                        pass.setText(PASS);
                                        fname.setText(FNAME);
                                        fno.setText(FNO);
                                        Button dialogButton = (Button) dialog.findViewById(R.id.butdissstudent);
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
    }
    private void getAllRecordsAndSetTOAdapter() {
        ArrayList<AttendanceModel> attendancerecords = sQLiteHelper.getAllRecordsStudentsAttendanceLater(m);
        adapter = new AttendanceCustomAdapter(this, attendancerecords ,sQLiteHelper,false);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllRecordsAndSetTOAdapter();
    }
}
