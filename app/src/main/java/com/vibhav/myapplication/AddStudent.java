package com.vibhav.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddStudent extends AppCompatActivity {
    EditText sName, DOB, fName, Number, fNumber, Mail, Address, Pass;
    byte[] image;
    Button b;
    ImageView img;
    RadioGroup Gender;
    StudentModel studenttemp;
    ClassModel classtemp;
    boolean z;
    String gender = "Male";
    SQLiteHelper sQLiteHelper;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        z =  (boolean) getIntent().getSerializableExtra("Flag");
        if(z)
        classtemp =  (ClassModel) getIntent().getSerializableExtra("Modify");
        else
            studenttemp =  (StudentModel) getIntent().getSerializableExtra("Modify");

        sQLiteHelper = new SQLiteHelper(AddStudent.this);
        sName = (EditText)findViewById(R.id.sname);
        Pass = (EditText)findViewById(R.id.spass);
        DOB = (EditText)findViewById(R.id.dob_student);
        fName = (EditText)findViewById(R.id.name_father);
        Number = (EditText)findViewById(R.id.no_student);
        fNumber = (EditText)findViewById(R.id.no_father);
        Mail = (EditText)findViewById(R.id.mail_student);
        Address = (EditText)findViewById(R.id.address_student);
        Gender = (RadioGroup) findViewById(R.id.gender_student);
        b = findViewById(R.id.button2);
        if(!z)
        b.setText("Modify");
        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioMale:
                        gender="Male";
                        break;

                    case R.id.radioFemale:
                        gender="Female";
                        break;
                }
            }
        });
        img = findViewById(R.id.photo_student);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else
                    {
                        pickimageFromGallery();
                    }

                }
                else
                {

                }
            }
        });
        if(!z)
        {
            sName.setText(studenttemp.getStudentName());
            Pass.setText(studenttemp.getPass());
            Number.setText(studenttemp.getSno());
            DOB.setText(studenttemp.getDob());
            Mail.setText(studenttemp.getMail());
            Address.setText(studenttemp.getAddress());
            fName.setText(studenttemp.getFname());
            fNumber.setText(studenttemp.getFno());
            RadioButton Male,Female;
            Male = findViewById(R.id.radioMale);
            Female = findViewById(R.id.radioFemale);
            if(studenttemp.getGender().equals("Male"))
            {
                Male.setChecked(true);
                Female.setChecked(false);
            }
            else
            {
                Male.setChecked(false);
                Female.setChecked(true);
            }
            byte[] data = studenttemp.getPhoto();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            img.setImageBitmap(bmp);
        }
    }

    private void pickimageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_CODE: {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    pickimageFromGallery();
                else
                    Toast.makeText(this, "Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
            img.setImageURI(data.getData());
        }

    }

    public void button(View V) {
        String cid;
        if(z)
        cid = classtemp.getcID();
        else
            cid = studenttemp.getcID();
        String pass = Pass.getText().toString();
        String sname = sName.getText().toString();
        String dob = DOB.getText().toString();
        String fname = fName.getText().toString();
        String number = Number.getText().toString();
        String fnumber = fNumber.getText().toString();
        String mail = Mail.getText().toString();
        String address = Address.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        StudentModel newStudent = new StudentModel();
        if((!pass.equals("")) && (!sname.equals("")) && (!dob.equals("")) && (!fname.equals("")) && (!number.equals("")) && (!fnumber.equals("")) && (!mail.equals("")) && (!address.equals("")))
        {
            if(!z)
                newStudent.setsID(studenttemp.getsID());
            newStudent.setPass(pass);
            newStudent.setStudentName(sname);
            newStudent.setcID(cid);
            newStudent.setAddress(address);
            newStudent.setDob(dob);
            newStudent.setFname(fname);
            newStudent.setFno(fnumber);
            newStudent.setGender(gender);
            newStudent.setMail(mail);
            newStudent.setSno(number);
            newStudent.setLast_date(date);
            Drawable d = img.getDrawable();
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            image = stream.toByteArray();
            newStudent.setPhoto(image);
            if(z)
            {
                sQLiteHelper.insertRecordStudent(newStudent);
                sQLiteHelper.insertRecordAttendanceLater(newStudent);
            }
            else
                sQLiteHelper.updateRecordStudent(newStudent);
            Toast.makeText(AddStudent.this,"Success", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
            Toast.makeText(AddStudent.this,"All fields are compulsory", Toast.LENGTH_SHORT).show();

    }
}
