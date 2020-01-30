package com.vibhav.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    RadioGroup radioLoginGroup;
    String LoginValidation="Admin";
    String Adminpass = "1234",Guestpass = "5678";
    int AdminId = 101 ,GuestId = 102;
    EditText pass , id;
    String pass_s , id_s;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        radioLoginGroup = (RadioGroup) findViewById(R.id.radio);
        radioLoginGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioadmin:
                        LoginValidation="Admin";
                        break;
                    case R.id.radioguest:
                        LoginValidation="Guest";
                        break;
                    case R.id.radioteacher:
                        LoginValidation="Teacher";
                        break;
                }
            }
        });
    }
    public void addListenerOnButton(View V)
    {

        id = (EditText) findViewById(R.id.id);
        pass = (EditText) findViewById(R.id.pass);
        id_s = id.getText().toString();
        pass_s = pass.getText().toString();
        if(LoginValidation.equals("Admin") && id_s.equals(AdminId+"")&& pass_s.equals(Adminpass))
        {
        Toast.makeText(LoginActivity.this, LoginValidation, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }
        else
            if(LoginValidation.equals("Guest"))
            {
                sqLiteHelper = new SQLiteHelper(LoginActivity.this);
                try
                {
                   boolean z = sqLiteHelper.passcheckStudent(id_s,pass_s);
                   if(z)
                   {
                        Toast.makeText(LoginActivity.this,"Student", Toast.LENGTH_SHORT).show();
                        StudentModel s = new StudentModel();
                        s.setsID(id_s);
                        Intent intent = new Intent(LoginActivity.this,StudentActivity.class);
                        intent.putExtra("Map", s);
                        startActivity(intent);
                   }
                   else
                       Toast.makeText(LoginActivity.this,"You are not registered", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(LoginActivity.this,"You are not registered", Toast.LENGTH_SHORT).show();
                }
            }
            else if(LoginValidation.equals("Teacher"))
            {
               sqLiteHelper = new SQLiteHelper(LoginActivity.this);
               try
               {
               boolean z = sqLiteHelper.passcheckTeacher(id_s,pass_s);
               if(z)
               {
                   Toast.makeText(LoginActivity.this,"Teacher", Toast.LENGTH_SHORT).show();
                   TeacherModel m = new TeacherModel();
                   m.setTeacherID(id_s);
                   Intent intent = new Intent(LoginActivity.this,TeacherMainActivity.class);
                   intent.putExtra("Map", m);
                   startActivity(intent);
               }
               else
                   Toast.makeText(LoginActivity.this,"You are not registered", Toast.LENGTH_SHORT).show();
               }
               catch (Exception e)
               {
                   Toast.makeText(LoginActivity.this,"You are not registered", Toast.LENGTH_SHORT).show();
               }
            }
            else
                Toast.makeText(LoginActivity.this,"Invalid Input", Toast.LENGTH_SHORT).show();
    }
}
