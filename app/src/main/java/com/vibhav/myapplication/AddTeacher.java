package com.vibhav.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddTeacher extends AppCompatActivity {
    EditText tName, tPass, tDob, tAddress, tMail, tNumber;
    RadioGroup tGender;
    byte[] image;
    ImageView img;
    String tgender = "Male";
    SQLiteHelper sQLiteHelper;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        sQLiteHelper = new SQLiteHelper(AddTeacher.this);
        tName = (EditText)findViewById(R.id.tname);
        tPass = (EditText)findViewById(R.id.tpass);
        tDob = (EditText)findViewById(R.id.teacher_dob);
        tAddress = (EditText)findViewById(R.id.teacher_address);
        tMail = (EditText)findViewById(R.id.teacher_mail);
        tNumber = (EditText)findViewById(R.id.teacher_num);
        tGender = findViewById(R.id.teacher_gender);
        tGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.teacher_male:
                        tgender="Male";
                        break;

                    case R.id.teacher_female:
                        tgender="Female";
                        break;
                }
            }
        });
        img = findViewById(R.id.teacher_photo);
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

        String tname = tName.getText().toString();
        String tpass = tPass.getText().toString();
        String tdob = tDob.getText().toString();
        String taddress = tAddress.getText().toString();
        String tmail = tMail.getText().toString();
        String tnum = tNumber.getText().toString();
        if((!tname.equals(""))&&(!tpass.equals(""))&&(!tdob.equals(""))&&(!taddress.equals(""))&&(!tmail.equals(""))&&(!tnum.equals("")))
        {TeacherModel newTeacher = new TeacherModel();
        newTeacher.setTeachName(tname);
        newTeacher.setTeachpass(tpass);
        newTeacher.setTeachdob(tdob);
        newTeacher.setTeachadd(taddress);
        newTeacher.setTeachmail(tmail);
        newTeacher.setTeachnum(tnum);
        newTeacher.setTeachgender(tgender);
        Drawable d = img.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
        newTeacher.setTeachphoto(image);
        sQLiteHelper.insertRecordTeacher(newTeacher);
        Toast.makeText(AddTeacher.this,"Success", Toast.LENGTH_SHORT).show();
        finish();}
        else
            Toast.makeText(AddTeacher.this,"All fields are compulsory", Toast.LENGTH_SHORT).show();

    }
}
